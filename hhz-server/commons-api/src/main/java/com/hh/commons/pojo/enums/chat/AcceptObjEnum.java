package com.hh.commons.pojo.enums.chat;

import com.hh.commons.pojo.enums.user.FriendRequestEnum;

/**
 * @Description: 聊天对象枚举类
 * @Author: hh
 * @Date: 2023/4/27 14:45
 * @Version: 1.0
 */
public enum AcceptObjEnum {
    UNKNOWN(0),
    ROOM(1),
    PEOPLE(2);

    private final int value;

    AcceptObjEnum(int value) {
        this.value = value;
    }

    public static AcceptObjEnum AcceptObjOf(int value) {
        for (AcceptObjEnum acceptObjEnum : AcceptObjEnum.values()) {
            if (acceptObjEnum.value == value) return acceptObjEnum;
        }
        return AcceptObjEnum.UNKNOWN;
    }
}
