package com.hh.chat.websocket;

import com.hh.commons.utils.RedisUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description:用户id和channel的关联关系处理
 * @Author: hh
 * @Date: 2023/1/28 16:01
 * @Version: 1.0
 */
@Component
public class UserChanelRel {

    @Autowired
    private RedisUtils redisUtils;

    private final static String userChannelSuffix = "_UserChannel";
    private final static int outTime = 10;

    private static final HashMap<String, Channel> channelHashManage = new HashMap<>();

    public void put(String senderId, Channel channel) {
        channelHashManage.put(senderId, channel);
        redisUtils.putValue(senderId + userChannelSuffix, senderId, outTime, TimeUnit.MINUTES);
    }

    public Channel get(String senderId) {
        String id = (String) redisUtils.getValue(senderId + userChannelSuffix);
        if (id == null) removeById(senderId);
        return channelHashManage.get(id);
    }

    public void removeById(String userId) {
        redisUtils.deleteValue(userId + userChannelSuffix);
        channelHashManage.remove(userId);
    }

    public String getUserId(Channel channel) {
        for (String userId : channelHashManage.keySet()) {
            if (channel == channelHashManage.get(userId)) return userId;
        }
        return "";
    }

}
