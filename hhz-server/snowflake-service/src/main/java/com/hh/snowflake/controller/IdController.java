package com.hh.snowflake.controller;

import com.hh.commons.common.ResultData;
import com.hh.commons.pojo.dto.snowflake.SnowflakeObj;
import com.hh.snowflake.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/id")
public class IdController {

    @Autowired
    private IdUtils idUtils;

    /**
     * 使用雪花算法生成唯一ID
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/snowflake")
    private ResultData getId() throws Exception {
        SnowflakeObj snowflakeObj = new SnowflakeObj(String.valueOf(idUtils.id()));
        return ResultData.success(snowflakeObj);
    }
}
