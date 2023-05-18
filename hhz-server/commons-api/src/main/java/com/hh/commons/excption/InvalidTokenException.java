package com.hh.commons.excption;

import lombok.*;

/**
 * @Description: 自定义无效token异常类
 * @Author: hh
 * @Date: 2023/1/10 16:55
 * @Version: 1.0
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvalidTokenException extends Exception {
    private String msg;
}
