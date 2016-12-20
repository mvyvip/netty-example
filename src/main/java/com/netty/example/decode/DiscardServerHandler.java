package com.netty.example.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by litao on 2016/12/14 0014.
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("DiscardServerHandler.channelRead0");
        System.out.println("Server: " + msg);
        //写给客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer("哦哦".getBytes()));
    }
}
