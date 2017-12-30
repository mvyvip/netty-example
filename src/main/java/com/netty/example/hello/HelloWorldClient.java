package com.netty.example.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HelloWorldClient {

    private static final String HOST = "127.0.0.1";
    
    private static final int PORT = 6666;

    public static void main(String[] args) throws Exception {
        EventLoopGroup workerGroup  = new NioEventLoopGroup();

        try {
            // 创建一个辅助类对Server进行一系列的配置
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)  // 只需要绑定一个线程组 因为他不需要处理连接
                    .channel(NioSocketChannel.class) // 指定通道
                    .handler(new HelloWorldClientInitializer());  // 自定义处理器

            Channel channel = bootstrap.connect(HOST, PORT).channel();
            // netty 默认发的是ByteBuf类型的数据 如果需要发送其他数据类型 比如string  则需要添加对应的解码器
            channel.writeAndFlush(Unpooled.copiedBuffer("123".getBytes()));
            channel.closeFuture().sync();
            System.out.println("Client handler closed");
        } finally {
            workerGroup.shutdownGracefully();
        }



    }
}
