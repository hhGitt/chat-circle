package com.hh.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 操作redis数据库相关工具类
 * @Author: hh
 * @Date: 2023/3/16 19:37
 * @Version: 1.0
 */

@Slf4j
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        log.info("Redis connection established successfully!: " + redisTemplate);
    }

    /**
     * redis存入数据并设置有效时间
     *
     * @param key      键名
     * @param value    值
     * @param time     保存时间
     * @param timeUnit 时间单位
     */
    public void putValue(String key, Object value, int time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * redis存入数据
     *
     * @param key   键名
     * @param value 值
     */
    public void putValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     *
     */
    public void incrementByKey(String key, int num) {
        redisTemplate.opsForValue().increment(key, num);
    }


    /**
     * 获取redis中的值
     *
     * @param key 键名
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * redis中删除键
     *
     * @param key
     * @return
     */
    public Boolean deleteValue(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * redis存入hash
     *
     * @param key   键名
     * @param value 值
     */
    public void putHashKey(String hashKey, String key, Object value) {
        redisTemplate.boundHashOps(hashKey).put(key, value);
    }

    /**
     * redis存入hash，并设置有效时间
     *
     * @param key   键名
     * @param value 值
     */
    public void putHashKey(String hashKey, String key, Object value, int time, TimeUnit timeUnit) {
        System.out.println(value);
        redisTemplate.boundHashOps(hashKey).put(key, value);
        redisTemplate.expire(hashKey + ":" + key, time, timeUnit);
    }

    /**
     * redis获取hash值
     *
     * @param hashKey
     * @return
     */
    public Object getHashKey(String hashKey) {
        return redisTemplate.boundHashOps(hashKey).get(hashKey);
    }

    /**
     * redis存入Set
     *
     * @param setKey 键名
     * @param value  值
     */
    public void putSetKey(String setKey, Object value) {
        redisTemplate.boundSetOps(setKey).add(value);
    }

    /**
     * redis存入Set，并设置有效时间
     *
     * @param setKey   键名
     * @param value    值
     * @param time     保存时间
     * @param timeUnit 时间单位
     */
    public void putSetKey(String setKey, Object value, int time, TimeUnit timeUnit) {
        redisTemplate.boundSetOps(setKey).add(value);
        redisTemplate.expire(setKey + ":" + value, time, timeUnit);
    }

    /**
     * redis获取Set值
     *
     * @param setKey
     * @return
     */
    public Object getSetKey(String setKey) {
        return redisTemplate.boundSetOps(setKey);
    }
}
