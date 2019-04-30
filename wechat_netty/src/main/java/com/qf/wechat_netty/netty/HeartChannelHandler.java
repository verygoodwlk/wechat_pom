package com.qf.wechat_netty.netty;

import com.qf.msg.WsHeartMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartChannelHandler extends SimpleChannelInboundHandler<WsHeartMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WsHeartMsg wsHeartMsg) throws Exception {
        System.out.println("接收到一个心跳消息：" + wsHeartMsg);
        ctx.writeAndFlush(wsHeartMsg);
    }
}
