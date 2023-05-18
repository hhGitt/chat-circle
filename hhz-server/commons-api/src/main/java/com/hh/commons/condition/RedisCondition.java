package com.hh.commons.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @Description: RedisUtils是否启动
 * @Author: hh
 * @Date: 2023/5/10 16:56
 * @Version: 1.0
 */

public class RedisCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.parseBoolean(context.getEnvironment().getProperty("component-switch.redis"));
    }
}