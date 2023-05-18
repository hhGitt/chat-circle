package com.hh.user.config;

import com.hh.user.config.properties.SMSProperties;
import com.hh.user.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @Description: 阿里短信业务配置类
* @Param:
* @return:
* @Author: hh
* @Date: 2023/5/15
*/

@Configuration
public class SMSConfig {

    @Autowired
    private SMSProperties smsProperties;

    @Bean
    public SMSUtils smsUtils() {
        return new SMSUtils(smsProperties);
    }
}
