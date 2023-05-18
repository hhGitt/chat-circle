package com.hh.chat.config;

import com.hh.chat.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 配置全局拦截器到spring中
 * @Author: hh
 * @Date: 2023/5/12 21:29
 * @Version: 1.0
 */
@Configuration
public class GlobalInterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private GlobalInterceptor globalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(globalInterceptor).addPathPatterns("/**"); //拦截的地址
    }
}
