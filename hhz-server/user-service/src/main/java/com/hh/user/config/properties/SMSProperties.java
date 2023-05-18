package com.hh.user.config.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 阿里短信相关配置文件
 * @Author: hh
 * @Date: 2023/5/14 16:03
 * @Version: 1.0
 */
@Component
@ConfigurationProperties("alisms")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SMSProperties {

    /**
     * 阿里的 AccessKeyId
     */
    private String accessKeyId;

    /**
     * 阿里的 AccessKeySecret
     */
    private String accessKeySecret;

    /**
     * 凭证类型，access_key、sts、ecs_ram_role、ram_role_arn、rsa_key_pair
     */
    private String type;

    private String endpoint;

    /**
     * 短信模板
     */
    private String templateCode;

    /**
     * 短信标题
     */
    private String signName;

    /**
     * 短信所需键值对
     */
    private String templateParam;
}
