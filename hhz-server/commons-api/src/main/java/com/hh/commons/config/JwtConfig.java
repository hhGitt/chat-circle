package com.hh.commons.config;

import com.hh.commons.condition.FastDFSCondition;
import com.hh.commons.condition.JwtCondition;
import com.hh.commons.config.properties.JwtProperties;
import com.hh.commons.utils.JwtUtils;
import com.hh.commons.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: JwtConfig配置类
 * @Author: hh
 * @Date: 2023/5/15 15:10
 * @Version: 1.0
 */
@Slf4j
@Configuration
@Conditional(JwtCondition.class)
public class JwtConfig {

    @Autowired
    private JwtProperties jwtProperties;

    @Bean
    public JwtUtils jwtUtils(RedisUtils redisUtils) {
        log.info("jwt中的redis：{}", redisUtils);
        return new JwtUtils(jwtProperties.getKey(), jwtProperties.getExpireTime(), jwtProperties.getSubject(), redisUtils);
    }
}
