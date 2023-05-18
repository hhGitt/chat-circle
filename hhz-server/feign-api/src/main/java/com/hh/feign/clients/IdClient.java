package com.hh.feign.clients;

import com.hh.commons.common.ResultData;
import com.hh.feign.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description: 请求snowflake-service服务相关接口
 * @Author: hh
 * @Date: 2023/3/16 15:53
 * @Version: 1.0
 */
@FeignClient(value = "snowflake-service", configuration = FeignConfiguration.class)
public interface IdClient {

    /**
     * 请求获取唯一id
     * @return
     */
    @GetMapping("/id/snowflake")
    ResultData getId();
}
