package com.hh.commons.pojo.enums.user;

import lombok.Getter;

/**
 * @Description: 好友添加枚举类
 * @Author: hh
 * @Date: 2023/4/21 11:30
 * @Version: 1.0
 */
@Getter
public enum FriendRequestEnum {
    UNPROCESSED(0),
    ACCEPT(1),
    REFUSE(2);

    private final int value;

    FriendRequestEnum(int value) {
        this.value = value;
    }

    public static FriendRequestEnum friendRequestOf(int value) {
        for (FriendRequestEnum friendRequestEnum : FriendRequestEnum.values()) {
            if (friendRequestEnum.value == value) return friendRequestEnum;
        }
        return FriendRequestEnum.UNPROCESSED;
    }

}
