package com.netty.example.heart;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by litao on 2016/12/20 0020.
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(!(evt instanceof IdleStateEvent)) {
            return ;
        }

        IdleStateEvent e = (IdleStateEvent) evt;
        if(e.state() == IdleState.READER_IDLE) {
            System.out.println("读入事件触发！");
        } else if(e.state() == IdleState.WRITER_IDLE) {
            System.out.println("写入事件触发！");
        } else if (e.state() == IdleState.ALL_IDLE) {
            System.out.println("所有事件触发！");
        }

        super.userEventTriggered(ctx, evt);
    }

    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("HeartBeatServerHandler.channelRead0");
    }
}
