package com.hh.chat;

import com.hh.chat.websocket.UserChanelRel;
import com.hh.commons.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.nio.channels.Channel;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: hh
 * @Date: 2023/5/6 16:15
 * @Version: 1.0
 */

@SpringBootTest
public class RedisTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void test1() {
        System.out.println(applicationContext.getBean(UserChanelRel.class));
    }
}
