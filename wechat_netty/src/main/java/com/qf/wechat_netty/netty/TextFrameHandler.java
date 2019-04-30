package com.qf.wechat_netty.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qf.msg.WsConnMsg;
import com.qf.msg.WsHeartMsg;
import com.qf.msg.WsMsg;
import com.qf.wechat_netty.util.ChannelGroupUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端连接成功！");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个客户端断开连接！");
        ChannelGroupUtil.removeChannel(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("获得文本数据：" + textWebSocketFrame.text());
        //获得Netty发送过来的消息，判断这个消息的类型
        String json = textWebSocketFrame.text();

        //解析
        WsMsg msg = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            Integer type = jsonObject.getInteger("type");

            switch (type){
                case 1:
                    //当前消息是一个初始化连接的消息
                    msg = JSON.parseObject(json, WsConnMsg.class);
                    break;
                case 2:
                    //当前消息是一个心跳消息
                    msg = JSON.parseObject(json, WsHeartMsg.class);
                    break;
            }

            //进行消息透传
            ctx.fireChannelRead(msg);

        } catch (Exception e) {
            System.out.println("消息解析失败，格式不正确；");
            ctx.close();
        }
    }

}
