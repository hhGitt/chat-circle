package com.hh.commons.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: JWT配置相关信息
 * @Author: hh
 * @Date: 2023/5/15 15:07
 * @Version: 1.0
 */
@Component
@ConfigurationProperties("jwt")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JwtProperties {
    /**
     * JWT加解密使用的密钥
     */
    private String key;
    /**
     * JWT存储的请求头
     */
    private String tokenHeader;
    /**
     * jwtToken的默认有效时间 单位分钟
     */
    private int expireTime;
    /**
     * JWT负载中拿到开头
     */
    private String tokenHead;
    /**
     * JWT主题
     */
    private String subject;

}
