package com.hh.chat.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Description: 配置拦截器，用于判断是否为指定地址请求
 * @Author: hh
 * @Date: 2023/5/12 21:08
 * @Version: 1.0
 */
@Slf4j
@Component
public class GlobalInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        String secretKey = request.getHeader("from");
        if (!StringUtils.isNotBlank(secretKey) || !secretKey.equals("gateway")) {
            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("error");
            return false;
        }
        return true;
    }
}