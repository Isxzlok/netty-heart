package com.xiongya.netty.heartBeat.client;

import com.xiongya.netty.heartBeat.entity.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;



/**
 * @Author xiongzhilong
 * @Date 2019-03-2615:17
 */
public class EchoClientHandle extends SimpleChannelInboundHandler<ByteBuf> {


    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                System.out.println("已经10秒没收到消息了");
                CustomProtocol heartBeat = new CustomProtocol(1001,"ping");
                //将信息发送给服务端，并通知channelfruture I/O操作完成
                ctx.writeAndFlush(heartBeat).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }

        super.userEventTriggered(ctx, evt);

    }


    //每当从服务器接收到新数据时，都会使用收到的消息调用此方法 channelRead0() ，在此示例中，接收到消息的类型是ByteBuf
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        //从服务端收到消息时调用
        System.out.println("客户端收到消息={"+byteBuf.toString(CharsetUtil.UTF_8)+"}");


    }


}
