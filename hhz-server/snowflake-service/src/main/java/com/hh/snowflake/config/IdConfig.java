package com.hh.snowflake.config;

import com.hh.snowflake.utils.IdUtils;
import com.hh.snowflake.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 雪花算法相关配置
 * @Author: hh
 * @Date: 2023/5/14 15:34
 * @Version: 1.0
 */
@Slf4j
@Configuration
public class IdConfig {
    @Value("${snowflake.workerId}")
    private long workerId; // 机器标识

    @Value("${snowflake.datacenterId}")
    private long datacenterId;  // 数据标识

    @Bean
    public SnowflakeIdWorker snowflakeIdWorker() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker();
        snowflakeIdWorker.setWorkerId(workerId);
        snowflakeIdWorker.setDatacenterId(datacenterId);
        return snowflakeIdWorker;
    }

    @Bean
    public IdUtils idUtils(SnowflakeIdWorker snowflakeIdWorker) {
        return new IdUtils(snowflakeIdWorker);
    }

}
