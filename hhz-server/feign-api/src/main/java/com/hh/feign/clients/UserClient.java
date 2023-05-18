package com.hh.feign.clients;

import com.hh.commons.common.ResultData;
import com.hh.feign.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @Description: 请求user-service服务相关接口
 * @Author: hh
 * @Date: 2023/3/16 15:53
 * @Version: 1.0
 */
@FeignClient(value = "user-service",configuration = FeignConfiguration.class)
public interface UserClient {

    /**
     * 请求用户信息
     * @param tokenHeader
     * @return
     */
    @GetMapping("/userinfo/")
    ResultData getUserInfo(@RequestHeader(name = "token") String tokenHeader);
}
