package com.netty.example.hellowithstring.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HelloWorldServer {

    private static final int PORT = 6789;

    public static void main(String[] args) throws Exception {
        // 第一个线程组 是用于接收Client端连接的线程数
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 第二个线程组 是用于实际的业务处理操作的线程数
        EventLoopGroup workerGroup  = new NioEventLoopGroup();

        try {
            // 创建一个辅助类对Server进行一系列的配置
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)  // 把俩个工作线程组加入进来
                    .channel(NioServerSocketChannel.class) // 指定通道
                    // handler和childHandler的区别是： handler在初始化时就会执行，而childHandler会在客户端成功connect后才执行
                    .handler(new LoggingHandler(LogLevel.INFO)) // 添加日志处理器
                    .childHandler(new HelloWorldServerInitializer());  // 自定义处理器

            // 绑定端口，开始接收进来的连接
            ChannelFuture future = serverBootstrap.bind(PORT).sync();
            // 应用程序阻塞在这里 直到channel关闭  也就是ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            // 跟Thread.sleep(Integer.MAX); 类似
            future.channel().closeFuture().sync();
            System.out.println("HelloWorldServer closed!");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }



    }
}
