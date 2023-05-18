package com.hh.user.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/***
* @Description: 验证码生成工具
* @Param:
* @return:
* @Author: hh
* @Date: 2023/5/14
*/

public class ValidateCodeUtils {

    /**
     * 随机生成指定长度含有数字和字母的验证码
     *
     * @param length
     * @return
     */
    public static String generateSNCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // 数据取0(数字)|1(字母),
            if (new Random().nextInt(2) == 0) {
                code.append(new Random().nextInt(10)); // 生成0-9的随机数字
            } else {
                code.append((char) (new Random().nextInt(57) + 65)); // 生成A-z的随机字母
            }
        }
        return code.toString();
    }

    /**
     * 随机生成指定长度字母的验证码
     *
     * @param length
     * @return
     */
    public static String generateSCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append((char) (new Random().nextInt(58) + 65)); // 生成A-z的随机字母
        }
        return code.toString();
    }

    /**
     * 随机生成指定长度数字的验证码
     *
     * @param length
     * @return
     */
    public static String generateNCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(new Random().nextInt(10)); // 生成0-9的随机数字
        }
        return code.toString();
    }
}
