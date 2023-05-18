package com.hh.commons.pojo.dto.snowflake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用于feign中传输生成的id
 * @Param:
 * @return:
 * @Author: hh
 * @Date: 2023/5/15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnowflakeObj {
    private String id;
}
