package com.xiongya.netty.heartBeat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.channels.SocketChannel;


/**
 * @Author xiongzhilong
 * @Date 2019-03-2615:42
 */

public class HeartbeatClient {


    private EventLoopGroup group = new NioEventLoopGroup();


    private int nettyPort = 8089;

    private String host = "127.0.0.1";

    private SocketChannel socketChannel;


    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                .channel(NioSocketChannel.class) //设置需要被实例化的socketChannel
                .handler(new CustomerHandleInitializer());

        //启动客户端
        //返回的ChannelFuture是用来保存channel操作的结果，就是进行调用任何操作io的方法都会立刻返回，
        // 但不能保证这些请求的io操作在调用结束之后会完成，只会返回一个ChannelFutrue实例，
        //用于保存操作io的结果或者状态
        ChannelFuture future = bootstrap.connect(host, nettyPort).sync();

        if (future.isSuccess()){
           System.out.println("启动netty成功");
        }

        socketChannel = (SocketChannel)future.channel();
    }

    public static void main(String []args) throws InterruptedException {

        new HeartbeatClient().start();
    }

}
