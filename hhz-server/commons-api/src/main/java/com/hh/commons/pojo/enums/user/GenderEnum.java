package com.hh.commons.pojo.enums.user;

import lombok.Getter;

/**
 * @Description: 性别枚举类
 * @Author hh
 * @Date 2023/1/7 15:44
 * @Version 1.0
 */
@Getter
public enum GenderEnum {
    UNKNOWN(0),
    MALE(1),
    FEMALE(2);
    private final int value;

    GenderEnum(int value) {
        this.value = value;
    }

    public static GenderEnum genderOf(int aGenderValue){
        for (GenderEnum gender : GenderEnum.values()) {
            if (gender.value == aGenderValue) {
                return gender;
            }
        }
        return GenderEnum.UNKNOWN;
    }
}
