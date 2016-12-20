package com.netty.example.fire;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by litao on 2016/12/14 0014.
 */
public class DiscardClient {
    private static final int PORT = 8612;
    private static final String HOST = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new StringDecoder());
                            p.addLast(new StringEncoder());
                            p.addLast(new DiscardClientHandler());
                            p.addLast(new DiscardClientHandler2());
                        }
                    });

            ChannelFuture f = b.connect(HOST, PORT).sync();
            f.channel().writeAndFlush("hello");

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
