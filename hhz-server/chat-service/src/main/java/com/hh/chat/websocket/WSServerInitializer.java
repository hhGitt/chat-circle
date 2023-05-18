package com.hh.chat.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: hh
 * @Date: 2023/1/17 14:39
 * @Version: 1.0
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 获取管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        // websocket 基于http协议，所需要的http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 在http上有一些数据流产生，需要使用netty对数据流写提供支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合处理，聚合成request或response
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
        /**
         * 帮你处理一些繁琐的事情
         * 处理握手动作:handshaking(close,ping,pong) ping+pong=心跳
         * 对于websocket来讲，都是以frams进行传输的，不同数据类型对于的frams也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义handler
        pipeline.addLast(new ChatHandler());
    }
}
