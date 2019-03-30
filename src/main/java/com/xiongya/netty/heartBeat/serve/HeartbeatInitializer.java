package com.xiongya.netty.heartBeat.serve;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @Author xiongzhilong
 * @Date 2019-03-2614:12
 */
public class HeartbeatInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                //五秒没收到消息，将IdleStateHandler添加到ChannelPipeline中
                //将IdleStateHandler添加到ChannelPipeline中，也会有一个定时任务，每五秒校验一次是否收到消息，否则就主动发送一次请求
                .addLast(new IdleStateHandler(5, 0, 0))
                .addLast(new HeartbeatDecoder())
                .addLast(new HeartBeatSimpleHandle());

    }
}
