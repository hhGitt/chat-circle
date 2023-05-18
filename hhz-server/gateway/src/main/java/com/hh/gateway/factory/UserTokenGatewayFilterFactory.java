package com.hh.gateway.factory;

import com.hh.gateway.filter.UserTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: 局部过滤器工厂（UserTokenGatewayFilter已转为全局过滤器）
 * @Author: hh
 * @Date: 2023/5/10 20:21
 * @Version: 1.0
 */
//@Component
//public class UserTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
//    @Autowired
//    private UserTokenFilter userTokenFilter;
//
//    @Override
//    public GatewayFilter apply(Object config) {
//        return userTokenFilter;
//    }
//}
