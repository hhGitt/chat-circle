package com.hh.gateway.filter;

import com.alibaba.nacos.common.utils.StringUtils;
import com.hh.commons.common.HttpStatus;
import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.entity.user.User;
import com.hh.commons.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 自定义全局过滤器，用于过滤没有token的请求
 * @Author: hh
 * @Date: 2023/5/10 15:25
 * @Version: 1.0
 */
@Slf4j
@Component
public class UserTokenFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtils jwtUtils;
    private static final String tokenHeader = "token";

    /**
     * 请求白名单
     */
    private static final List<String> skipUris = Arrays.asList(
            "/**/swagger-ui/**",
            "/**/**/v2/api-docs",
            "/**/swagger-resources/**",
            "/us/user",
            "/us/user/**",
            "/us/druid/**",
            "/si/id/snowflake",
            "/swagger-ui/**"
    );

    /**
     * token过滤
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate().header("from", "gateway").build();
        if (isSkip(exchange.getRequest().getPath().toString())) {
            return chain.filter(exchange.mutate().request(request.mutate().build()).build());
        }
        HttpHeaders headers = request.getHeaders();
        String jwtToken = headers.getFirst(tokenHeader);
        if (StringUtils.isEmpty(jwtToken)) {
            ResultData<Object> data = ResultData.fail(HttpStatus.USER_ACCOUNT_NOT_EXIST.getCode(), HttpStatus.USER_ACCOUNT_NOT_EXIST.getMessage());
            byte[] bytes = JSON.toJSONBytes(data);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
        // 验证token
        User user = null;
        try {
            user = jwtUtils.verifyJwtToken(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 验证成功后,如果令牌有效时间<=10分钟,则签发新的令牌,刷新令牌时间
        if (user != null) {
            if (user.getExpireTime() - System.currentTimeMillis() <= 1000L * 60 * 10) jwtUtils.refreshToken(user);
            return chain.filter(exchange.mutate().request(request.mutate().build()).build());
        } else {
            ResultData<Object> data = ResultData.fail(HttpStatus.TIMEOUT_TOKEN.getCode(), HttpStatus.TIMEOUT_TOKEN.getMessage());
            byte[] bytes = JSON.toJSONBytes(data);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
    }


    @Override
    public int getOrder() {
        return 1;
    }

    private boolean isSkip(String path) {
        log.info("request path: {}", path);
        for (String skipUri : skipUris) {
            if (skipUri.equals(path) || new AntPathMatcher().match(skipUri, path)) {
                log.info("pass url: {}", path);
                return true;
            }
        }
        return false;
    }
}
