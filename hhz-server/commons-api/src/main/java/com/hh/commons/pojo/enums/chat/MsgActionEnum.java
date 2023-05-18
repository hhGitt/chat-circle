package com.hh.commons.pojo.enums.chat;

import lombok.Getter;

/**
 * @Description: 聊天接收消息类型
 * @Author: hh
 * @Date: 2023/1/28 14:46
 * @Version: 1.0
 */
@Getter
public enum MsgActionEnum {
    CONNECT(1, "第一次(或重连)初始化连接"),
    CHAT(2, "聊天信息"),
    SIGNED(3, "消息签收"),
    KEEPALIVE(4, "客户端保持心跳"),
    PULL_FRIEND(5, "拉取好友"),
    Enter_ChatRoom(6, "进入聊天室"),
    Leave_ChatRoom(7, "离开聊天室");

    private final int type;
    private final String content;

    MsgActionEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

}
