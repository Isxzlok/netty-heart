package com.xiongya.netty.heartBeat.serve;

import com.xiongya.netty.heartBeat.entity.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;


/**
 * @Author xiongzhilong
 * @Date 2019-03-2610:17
 */
public class HeartBeatSimpleHandle extends SimpleChannelInboundHandler<CustomProtocol> {



    //将customprotocol转换成字节数据存入byteBuf这个数据容器中
    private static final ByteBuf byteBuf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(new CustomProtocol(123456L,"pong").toString(), CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                System.out.println("已经5秒没有收到信息！");
                //向客户端发送消息
                ctx.writeAndFlush(byteBuf).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    //断开连接时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //取消绑定
        NettySocketHolder.remove((NioSocketChannel) ctx.channel());

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol customProtocol) throws Exception {

        System.out.println("收到customProtocol={"+customProtocol+"}");

        //调用writeAndFlush（Object）来逐字写入接收到的信息并刷新线路（其实就是将受到的信息返回给客户端）
        //ctx.writeAndFlush(customProtocol);

        //保存客户端与channel之间的关系(收到客户端发送来的消息（也就是一个customProtocol对象）之后，将customProtocol对象中的id和传输信息的通道保存进map集合)
        NettySocketHolder.put(customProtocol.getId(), (NioSocketChannel) ctx.channel());


    }
}
