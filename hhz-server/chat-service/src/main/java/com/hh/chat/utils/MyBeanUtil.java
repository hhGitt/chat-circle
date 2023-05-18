package com.hh.chat.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description: 获取spring容器中的对象
 * @Author: hh
 * @Date: 2023/5/6 20:15
 * @Version: 1.0
 */
@Component
public class MyBeanUtil implements ApplicationContextAware {

    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        if (applicationContext == null) {
            applicationContext = app;
        }
    }

    /**
     * 通过类的class从容器中手动获取对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
