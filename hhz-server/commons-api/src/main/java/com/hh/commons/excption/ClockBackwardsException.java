package com.hh.commons.excption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 自定义时钟超时异常
 * @Author: hh
 * @Date: 2023/1/10 16:55
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClockBackwardsException extends Exception {
    private long timestamp;
}
