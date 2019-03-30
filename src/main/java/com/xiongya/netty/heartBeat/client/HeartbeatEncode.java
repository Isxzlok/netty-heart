package com.xiongya.netty.heartBeat.client;

import com.xiongya.netty.heartBeat.entity.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author xiongzhilong
 * @Date 2019-03-2615:54
 */
public class HeartbeatEncode extends MessageToByteEncoder<CustomProtocol> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CustomProtocol customProtocol, ByteBuf byteBuf) throws Exception {

        byteBuf.writeLong(customProtocol.getId());
        byteBuf.writeBytes(customProtocol.getContent().getBytes());
    }
}
