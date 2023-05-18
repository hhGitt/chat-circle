package com.hh.commons.common;

import lombok.AllArgsConstructor;

/**
 * @Description: 自定义响应状态码
 * @Author: hh
 * @Date: 2023/1/10 16:55
 * @Version: 1.0
 */

@AllArgsConstructor
public enum HttpStatus {
    SUCCESS(200, "操作成功"),
    NO_PERMISSION(403, "无访问权限,请联系管理员授予权限"),
    ACCESS_FAILED(401, "匿名用户访问无权限资源时的异常"),
    REQUEST_METHOD_NOT_EXIST(404, "请求路径方法不存在"),
    FAIL(500, "系统异常，请稍后重试"),
    SEND_FAIL(500, "短信业务异常，请稍后重试"),
    INVALID_TOKEN(2001, "访问令牌不合法"),
    TIMEOUT_TOKEN(2002, "访问令牌已失效"),
    ERROR_TOKEN(2003, "令牌验签失败:"),
    CLIENT_AUTHENTICATION_FAILED(1001, "客户端认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002, "用户名或密码错误"),
    USER_ACCOUNT_EXIST(1003, "用户账号已存在"),
    PHONE_FORMAT_ERROR(1004, "手机号格式有误"),
    EMAIL_FORMAT_ERROR(1004, "邮箱格式有误"),
    REPEAT_CODE_REQUEST(1005, "重复发送验证码请求"),
    NO_CODE_REQUEST(1005, "验证码超时或未请求"),
    CODE_ERROR(1005, "验证码错误"),
    NO_LOGIN_INFO(1006, "无登录账号"),
    UPDATE_INFO_ERROR(1007, "信息更新失败"),
    USER_ACCOUNT_NOT_EXIST(1008, "用户不存在或身份已过期"),
    CREATE_ROOM_FAILED(1009, "创建房间失败"),
    START_ROOM_FAILED(1010, "开启房间失败，房间不存在或已开启"),
    USER_ALREADY_FRIEND(1011, "用户已经是好友"),
    USER_REQUEST_REPEAT(1012, "重复发送请求"),
    TIMEOUT_CREATE_ID(8001, "生成ID超时");
    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
