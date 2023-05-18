package com.hh.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.hh.feign.clients")
@ComponentScan(basePackages = {"com.hh.commons","com.hh.*"})
@MapperScan("com.hh.chat.mapper")
public class ChatApplication {

    public static void main(String[] args) {
        disableWarning(); //禁用警告
        SpringApplication.run(ChatApplication.class, args);
    }

    public static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);
            Class<?> cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }
}
