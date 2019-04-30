package com.qf.wechat_netty.netty;

import com.qf.msg.WsConnMsg;
import com.qf.msg.WsShutDownMsg;
import com.qf.wechat_netty.util.ChannelGroupUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
@ChannelHandler.Sharable
public class ConnChannelHandler extends SimpleChannelInboundHandler<WsConnMsg> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WsConnMsg wsConnMsg) throws Exception {
        //当前连接的用户信息
        String username = wsConnMsg.getUsername();
        //当前的设备号
        String deviceid = wsConnMsg.getDeviceid();

        //判断这个账号有没有在其他的设备上登录
        String oldDeviceId = (String) redisTemplate.opsForValue().get(username);
        if(oldDeviceId != null && !oldDeviceId.equals(deviceid)){
            //发送挤下线的信息 - oldDeviceId
            WsShutDownMsg shutDownMsg = new WsShutDownMsg(username);
            shutDownMsg.setType(3);
            //获得需要下线的设备对应的Channel对象
            Channel channel = ChannelGroupUtil.getChannel(oldDeviceId);
            if(channel != null){
                //刚好这个设备连接的就是当前机器
                channel.writeAndFlush(shutDownMsg);
            } else {
                //要么连了别的机器，要么就没在线
                //通过rabbitmq广播下线消息给其他的Netty服务器
            }
        }

        //将设备号 - Channel对象保存到Map集合中
        ChannelGroupUtil.add(deviceid, ctx.channel());
        //将用户的username - 设备号保存到redis中
        redisTemplate.opsForValue().set(username, deviceid);
    }
}
