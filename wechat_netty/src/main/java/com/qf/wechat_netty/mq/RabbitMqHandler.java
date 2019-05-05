package com.qf.wechat_netty.mq;

import com.qf.entity.Msg;
import com.qf.wechat_netty.util.ChannelGroupUtil;
import io.netty.channel.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = "netty_queue_${netty.ip}:${netty.port}")
    public void rabbitHandler(Msg msg){
        System.out.println("接收到消息：" + msg);

        //判断该消息接收方是否在当前机器上
        //接收方的id
        int toid = msg.getToid();
        String deviedid = (String) redisTemplate.opsForValue().get(toid + "");
        Channel channel = ChannelGroupUtil.getChannel(deviedid);
        System.out.println("设备号：" + deviedid + " " + channel);
        if(channel != null){
            //当前账号在线并且连接到当前服务器上
            msg.setCreatetime(null);
            channel.writeAndFlush(msg);
        }
    }
}
