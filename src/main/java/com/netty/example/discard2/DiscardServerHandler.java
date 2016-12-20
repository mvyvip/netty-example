package com.netty.example.discard2;

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
        System.out.println("Server: " + msg);
        //写给客户端 并关闭客户端的连接
        ctx.writeAndFlush("world").addListener(ChannelFutureListener.CLOSE);
    }
}
