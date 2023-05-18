package com.hh.user.config;

import com.hh.user.interceptor.AccessLimitInterceptor;
import com.hh.user.interceptor.GlobalInterceptor;
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
    @Autowired
    private AccessLimitInterceptor accessLimitInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor).addPathPatterns("/**"); //拦截的地址
        registry.addInterceptor(accessLimitInterceptor).addPathPatterns("/**");
    }
}
