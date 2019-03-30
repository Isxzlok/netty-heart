package com.xiongya.netty.heartBeat.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Author xiongzhilong
 * @Date 2019-03-2615:52
 */
public class CustomerHandleInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                //10秒没法送信息，给IdleStateHandle 添加到ChannelPipeline
                .addLast(new IdleStateHandler(0,10,0))
                .addLast(new HeartbeatEncode())
                .addLast(new EchoClientHandle());

    }
}
