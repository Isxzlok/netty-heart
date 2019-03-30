package com.xiongya.netty.heartBeat.serve;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xiongzhilong
 * @Date 2019-03-2611:04
 */
public class NettySocketHolder {

    //将客户端id和sockechannel(客户端通道)进行关联
    private static final Map<Long, NioSocketChannel> map = new ConcurrentHashMap<Long, NioSocketChannel>(16);

    public  static void put(long id, NioSocketChannel socketChannel){
        map.put(id, socketChannel);
    }

    public static NioSocketChannel get(Long id){
        return map.get(id);
    }

    public static Map<Long, NioSocketChannel> getMap(){
        return map;
    }

    public static void remove(NioSocketChannel nioSocketChannel){
        /*map.entrySet().stream()
                .filter(entry -> entry.getValue() == nioSocketChannel)
                .forEach(entry -> map.remove(entry.getKey()));
                */

        for (Map.Entry entry :map.entrySet()){
            if (entry.getValue() == nioSocketChannel){
                map.remove(entry.getKey());
            }

        }
    }
}
