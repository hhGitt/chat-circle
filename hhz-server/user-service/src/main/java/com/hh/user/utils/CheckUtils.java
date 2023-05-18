package com.hh.user.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
/***
* @Description: 判断手机号格式工具类
* @Param:
* @return:
* @Author: hh
* @Date: 2023/5/14
*/

public class CheckUtils {

    /**
     * 中国电信号码格式验证 手机段： 133,149,153,173,177,180,181,189,191,199,1349,1410,1700,1701,1702
     **/
    private static final String CHINA_TELECOM_PATTERN = "(?:^(?:\\+86)?1(?:33|49|53|7[37]|8[019]|9[19])\\d{8}$)|(?:^(?:\\+86)?1349\\d{7}$)|(?:^(?:\\+86)?1410\\d{7}$)|(?:^(?:\\+86)?170[0-2]\\d{7}$)";

    /**
     * 中国联通号码格式验证 手机段：130,131,132,145,146,155,156,166,171,175,176,185,186,1704,1707,1708,1709
     **/
    private static final String CHINA_UNICOM_PATTERN = "(?:^(?:\\+86)?1(?:3[0-2]|4[56]|5[56]|66|7[156]|8[56])\\d{8}$)|(?:^(?:\\+86)?170[47-9]\\d{7}$)";

    /**
     * 中国移动号码格式验证
     * 手机段：134,135,136,137,138,139,147,148,150,151,152,157,158,159,178,182,183,184,187,188,195,198,1440,1703,1705,1706
     **/
    private static final String CHINA_MOBILE_PATTERN = "(?:^(?:\\+86)?1(?:3[4-9]|4[78]|5[0-27-9]|78|8[2-478]|98|95)\\d{8}$)|(?:^(?:\\+86)?1440\\d{7}$)|(?:^(?:\\+86)?170[356]\\d{7}$)";

    /**
     * 中国香港
     */
    private static final String CHINA_HANGKONG_PATTERN = "^(00)?(852)[5|6|9]\\d{7}$";

    /**
     * 邮箱格式
     */
    private static final String EMAIL_PATTERN = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";


    /**
     * 判断是否为有效邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkMail(String email) {
        if (StringUtils.isNotBlank(email)) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            return pattern.matcher(email).matches();
        }
        return false;
    }


    /**
     * 中国大陆手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            return checkChinaMobile(phone) || checkChinaUnicom(phone) || checkChinaTelecom(phone) || isHKPhoneLegal(phone);
        }
        return false;
    }

    /**
     * 中国香港手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean checkChinaHangkong(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            Pattern regexp = Pattern.compile(CHINA_HANGKONG_PATTERN);
            return regexp.matcher(phone).matches();
        }
        return false;
    }

    /**
     * 中国移动手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean checkChinaMobile(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            Pattern regexp = Pattern.compile(CHINA_MOBILE_PATTERN);
            return regexp.matcher(phone).matches();
        }
        return false;
    }

    /**
     * 中国联通手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean checkChinaUnicom(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            Pattern regexp = Pattern.compile(CHINA_UNICOM_PATTERN);
            return regexp.matcher(phone).matches();
        }
        return false;
    }

    /**
     * 中国电信手机号码校验
     *
     * @param phone
     * @return
     */
    public static boolean checkChinaTelecom(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            Pattern regexp = Pattern.compile(CHINA_TELECOM_PATTERN);
            return regexp.matcher(phone).matches();
        }
        return false;
    }

    /**
     * 香港手机号校验
     * 香港区号：00852
     * 香港手机号码8位数，5|6|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String phone) throws PatternSyntaxException {
        if (StringUtils.isNotBlank(phone)) {
            Pattern regexp = Pattern.compile(CHINA_HANGKONG_PATTERN);
            return regexp.matcher(phone).matches();
        }
        return false;
    }

}