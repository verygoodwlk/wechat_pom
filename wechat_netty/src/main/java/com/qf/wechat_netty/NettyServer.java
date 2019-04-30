package com.qf.wechat_netty;

import com.qf.wechat_netty.netty.ConnChannelHandler;
import com.qf.wechat_netty.netty.HeartChannelHandler;
import com.qf.wechat_netty.netty.MsgOutChannelHandler;
import com.qf.wechat_netty.netty.TextFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NettyServer implements CommandLineRunner {


    @Value("${netty.port}")
    private int port;
    @Value("${netty.ip}")
    private String ip;
    @Value("${zk.host}")
    private String zkHost;

    @Autowired
    private ConnChannelHandler connChannelHandler;

    private EventLoopGroup master = new NioEventLoopGroup();
    private EventLoopGroup slave = new NioEventLoopGroup();

    /**
     * 初始化服务端
     */
    private void init() throws InterruptedException {

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(master, slave)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();


                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
                        pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                        pipeline.addLast(new MsgOutChannelHandler());
                        pipeline.addLast(new ReadTimeoutHandler(1000 * 60 * 2));
                        //自定义的消息解析的Handler
                        pipeline.addLast(new TextFrameHandler());
                        //处理连接初始化的消息
                        pipeline.addLast(connChannelHandler);
                        //处理心跳消息
                        pipeline.addLast(new HeartChannelHandler());


                    }
                });
        ChannelFuture bindFuture = serverBootstrap.bind(port);
        bindFuture.sync();
        System.out.println("Netty服务器已经正常启动，端口为：" + port);

        //调用一下zookeeper将当前的服务注册到zookeeper之上
        createNode2zk();

        //同步阻塞当前线程
        Channel channel = bindFuture.channel();
        channel.closeFuture().sync();
    }

    /**
     * 去zookeeper上创建一个服务节点
     */
    private void createNode2zk(){

        //连接zookeeper服务
        ZkClient zkClient = new ZkClient(zkHost, 10000);

        //判断是否存在，存在则创建一个/netty的永久节点
        boolean exists = zkClient.exists("/netty");
        if (!exists) {
            //创建节点
            zkClient.createPersistent("/netty");
        }

        //创建当前服务器的临时节点， 节点名称就是当前机器的[ip:port]
        //获得本机ip
        String node = "/netty/" + ip + ":" + port;
        boolean nodes = zkClient.exists(node);
        if(nodes){
            zkClient.delete(node);
        }

        zkClient.createEphemeral("/netty/" + ip + ":" + port);

    }

    /**
     * 这个方法会在SPringboot初始化的时候调用
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //启动Netty服务
        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            master.shutdownGracefully();
            slave.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        //连接zookeeper服务
        ZkClient zkClient = new ZkClient("47.112.207.98:2181", 10000);
        List<String> children = zkClient.getChildren("/netty");
        System.out.println("/netty下面的节点：" + children);
    }
}
