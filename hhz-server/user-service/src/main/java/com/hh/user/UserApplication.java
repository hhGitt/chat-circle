package com.hh.user;

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
@ComponentScan(basePackages = {"com.hh.commons","com.hh.user"})
@MapperScan("com.hh.user.mapper")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}

