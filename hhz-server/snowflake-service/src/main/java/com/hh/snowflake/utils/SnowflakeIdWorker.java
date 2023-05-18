package com.hh.snowflake.utils;

import com.hh.commons.excption.ClockBackwardsException;
import org.springframework.stereotype.Component;


/**
 * @Description: Twitter的分布式自增ID算法snowflake
 * @Author: hh
 * @Date: 2023/1/14 15:26
 * @Version: 1.0
 */

public class SnowflakeIdWorker {
    /**
     * 第1部分(41位)
     * 开始时间截(2022- 04 -01 )
     */
    private final long startTime = 1648742400000L;
    /**
     * 第2部分(10位)
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;
    /**
     * 数据标识id所占的位数
     */
    private final long datacenterIdBits = 5L;
    /**
     * 第3部分(12位)
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;
    /**
     * -1L^(-1L<<5) = 31
     * 支持的最大机器id，结果是31
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * -1L^(-1L<<5)=31
     * 支持的最大数据标识id,结果是31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /**
     * -1L^(-1L<<12)=4095
     * 自增长最大值4095，0开始
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits;
    /**
     * 工作机器ID(0~31)
     */
    private long workerId;
    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;
    /**
     * 1毫秒内序号(0~4095 )
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    public SnowflakeIdWorker() {
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 配置workerId(0~31)
     * @param workerId
     */
    public void setWorkerId(long workerId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        this.workerId = workerId;
    }

    /**
     * 配置datacenterId(0~31)
     * @param datacenterId
     */
    public void setDatacenterId(long datacenterId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.datacenterId = datacenterId;
    }

    public long getWorkerId() {
        return workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() throws Exception {
        long timestamp = timeGen();
        //如果当前时间小于上一次ID生成的时 间戳，说明系统时钟回退过这个时候应当抛出异
        //发生了回拨，此刻时间小于上次发号时间
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    //时间偏差大小小于5ms，则等待两倍时间
                    wait(offset << 1);//wait
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        //还是小于，抛异常并上报
                        throw new ClockBackwardsException(timestamp);
                    }
                } catch (InterruptedException e) {
                    throw e;
                }
            } else {
                //throw
                throw new ClockBackwardsException(timestamp);
            }
        }
        //如果是同一时间生成的，则进行亳秒内序列 分配ID
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            //sequence == 0，就是1毫秒用完了4096个数
            if (sequence == 0) {
                //阳塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }
        // 上次生成ID的时间截
        lastTimestamp = timestamp;
        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - startTime) << timestampLeftShift) // 时间戳左移22位
                | (datacenterId << datacenterIdShift) // 数据标识左移17位
                | (workerId << workerIdShift) //机器id标识左移12位
                | sequence;

    }

    /**
     * 阻塞到下一个亳秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return当前时间(亳秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}