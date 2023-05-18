package com.hh.commons.excption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 自定义token过期异常类
 * @Author: hh
 * @Date: 2023/1/10 16:55
 * @Version: 1.0
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeOutTokenException extends Exception {
    private String msg;
}
