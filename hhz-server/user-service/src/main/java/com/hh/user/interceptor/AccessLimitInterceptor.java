package com.hh.user.interceptor;

import com.alibaba.fastjson.JSON;
import com.hh.commons.annotations.AccessLimit;
import com.hh.commons.common.ResultData;
import com.hh.commons.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 用于限制同一用户限制多次访问同一个接口
 * @Author: hh
 * @Date: 2023/5/18 16:08
 * @Version: 1.0
 */
@Component
@Slf4j
public class AccessLimitInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            // 方法上没有该注解
            if (accessLimit == null) return true;
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            String ip = request.getHeader("x-forwarded-for");
            String method = request.getMethod();
            String requestURI = request.getRequestURI();
            String redisKey = ip + ":" + method + ":" + requestURI;
            Integer count = (Integer) redisUtils.getValue(redisKey);
            if (count == null) {
                redisUtils.putValue(redisKey, 1, seconds, TimeUnit.SECONDS);
            } else {
                if (count >= maxCount) {
                    // 超出访问次数
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    ResultData result = ResultData.fail(403, accessLimit.msg());
                    out.write(String.valueOf(JSON.toJSON(result)));
                    out.flush();
                    out.close();
                    return false;
                } else {
                    redisUtils.incrementByKey(redisKey, 1);
                }
            }
        }
        return true;
    }
}
