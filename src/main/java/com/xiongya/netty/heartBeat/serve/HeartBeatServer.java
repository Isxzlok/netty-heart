package com.xiongya.netty.heartBeat.serve;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;

import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @Author xiongzhilong
 * @Date 2019-03-2611:39
 */

public class HeartBeatServer {



    private EventLoopGroup boss = new NioEventLoopGroup();

    private EventLoopGroup work = new NioEventLoopGroup();


    private int nettyPort = 8089;


    //被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Serclet的inti()方法。
    // 被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
    //@PostConstruct
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();//serverBootStrap是一个设置服务器的帮助程序类
        /*NioEventLoopGroup是一个处理I/O操作的多线程时间循环。Netty为不同类型的创术提供了各种EventLoopGroup实现，
        我们在此示例中实现了服务器端应用程序，因此将使用两个NioEventLoopGroup。第一个，通常称为"老板"，接受传入连接，
        第二个，通常称为"工人"，一旦看板接受并将接受的连接注册到工作人员，就处理被接受连接的流量，使用了多少个线程以及他们如何
        映射到创建的channel取决于EventLoopGroup实现，甚至可以通过构造函数配置
        */
        bootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class) //指定使用NioServerSocketChannel类，该类用于实例化新的channel以接受传入的连接
                .localAddress(new InetSocketAddress(nettyPort)) //指定需要监听的端口
                .childOption(ChannelOption.SO_KEEPALIVE, true) //保持长连接
                .childHandler(new HeartbeatInitializer());//childHandler()方法需要一个ChannelInitializer类,ChannelInitializer是一个特殊的处理程序，旨在帮助用户配置新的Channel,您最有可能希望通过添加一些处理程序（如DiscardServerHandler）来配置新Channel的ChannelPipeline，以实现您的网络应用程序。 随着应用程序变得复杂，您可能会向管道添加更多处理程序，并最终将此匿名类提取到顶级类中。

        //绑定并开始接受传入的连接
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()){
            System.out.println("启动netty成功");
        }

        future.channel().closeFuture().sync(); //阻塞当前线程直到服务端关闭（主线程在等待子线程的退出）


    }

    //@PreDestroy  //被@PreDestroy修饰的方法会在服务器卸载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的destroy()方法。
    // 被@PreDestroy修饰的方法会在destroy()方法之后运行，在Servlet被彻底卸载之前
    public void destory(){
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        System.out.println("关闭netty成功");

    }

    public static void main(String []args) throws InterruptedException {
         HeartBeatServer h = new HeartBeatServer();
         h.start();
         h.destory();
    }


}

