package com.xiongya.netty.heartBeat.serve;

import com.xiongya.netty.heartBeat.entity.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author xiongzhilong
 * @Date 2019-03-2614:24
 */

/*
服务端解码器，服务端于客户端采用的是自定义的pojo进行通讯的，所以需要在客户端进行编码，服务端进行解码，
也都只需要各自实现一个编解码器即可
 */
public class HeartbeatDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        long id = byteBuf.readLong();
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        String content = new String(bytes);
        CustomProtocol customProtocol = new CustomProtocol();
        customProtocol.setId(id);
        customProtocol.setContent(content);
        list.add(customProtocol);
    }
}
