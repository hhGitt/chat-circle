package com.hh.feign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Feign调用的时候添加请求头from
 * @Author: hh
 * @Date: 2023/5/12 21:11
 * @Version: 1.0
 */
@Configuration
public class FeignConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("from", "gateway");
    }
}
