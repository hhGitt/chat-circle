package com.hh.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 自定义接口限流注解
 * @Author: hh
 * @Date: 2023/5/18 16:04
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    /**
     * 限制周期（秒）
     */
    int seconds();

    /**
     * 规定周期内限制次数
     */
    int maxCount();

    /**
     * 触发限制时的消息提示
     */
    String msg() default "操作频率过高";
}
