package com.qf.wechat_netty.netty;

import com.alibaba.fastjson.JSON;
import com.qf.msg.WsMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 出站消息的预处理
 * 将对象 -> 文本帧
 */

public class MsgOutChannelHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        if(msg instanceof WsMsg) {
            //提前处理msg
            String json = JSON.toJSONString(msg);
            msg = new TextWebSocketFrame(json);
        }

        super.write(ctx, msg, promise);
    }
}
