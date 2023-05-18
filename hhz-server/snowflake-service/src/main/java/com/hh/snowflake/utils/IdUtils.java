package com.hh.snowflake.utils;

import com.hh.commons.excption.ClockBackwardsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/***
 * @Description: 雪花算法实现数据库id
 * @Param:
 * @return:
 * @Author: hh
 * @Date: 2023/5/14
 */

@Slf4j
public class IdUtils {

    private final SnowflakeIdWorker worker;

    public IdUtils(SnowflakeIdWorker snowflakeIdWorker) {
        log.info("SnowflakeIdWorker 配置: WorkerId: {},DatacenterId: {}", snowflakeIdWorker.getWorkerId(), snowflakeIdWorker.getDatacenterId());
        this.worker = snowflakeIdWorker;
    }

    public long id() throws Exception {
        if (worker == null) {
            log.error("SnowflakeIdWorker 未配置!");
            return -1;
        }
        return worker.nextId();
    }
}