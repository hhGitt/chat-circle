package com.hh.commons.common;

import lombok.Data;

/**
 * @Description: 自定义请求返回数据
 * @Author: hh
 * @Date: 2023/1/10 16:55
 * @Version: 1.0
 */

@Data
public class ResultData<T> {
    /**
     * 结果状态 ,具体状态码参见ResultData.java
     */
    private int code;
    private String message;
    private boolean success;
    private T data;

    public ResultData() {

    }

    public static <T> ResultData<T> success() {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(HttpStatus.SUCCESS.getCode());
        resultData.setMessage(HttpStatus.SUCCESS.getMessage());
        resultData.setSuccess(true);
        return resultData;
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(HttpStatus.SUCCESS.getCode());
        resultData.setMessage(HttpStatus.SUCCESS.getMessage());
        resultData.setData(data);
        resultData.setSuccess(true);
        return resultData;
    }

    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setSuccess(false);
        return resultData;
    }

}