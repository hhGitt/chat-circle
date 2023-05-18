package com.hh.chat.config;

import com.hh.chat.service.ChatRoomService;
import com.hh.chat.websocket.RoomsChanelRel;
import com.hh.chat.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 配置netty启动
 * @Author: hh
 * @Date: 2023/2/1 16:45
 * @Version: 1.0
 */

@Slf4j
@Configuration
public class NettyConfig {

    @Bean
    public WebSocketServer createNettyServer(ChatRoomService chatRoomService){
        WebSocketServer nettyServer = new WebSocketServer();
        new Thread(nettyServer::start).start();
        log.info("WebSocketServer start!!!");
        RoomsChanelRel.init(chatRoomService);
        return nettyServer;
    }

}

