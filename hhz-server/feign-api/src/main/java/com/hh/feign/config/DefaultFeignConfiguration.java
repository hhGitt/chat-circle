package com.hh.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Feign日志配置
 * @Author: hh
 * @Date: 2023/3/16 15:53
 * @Version: 1.0
 */
@Configuration
public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level logLevel() {
        return Logger.Level.NONE;
    }
}
