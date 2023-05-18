package com.hh.user.controller.utils;


import com.hh.commons.common.HttpStatus;
import com.hh.commons.common.ResultData;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//作为springmvc的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {

    /**
     * 处理请求路径方法不存在
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultData doHttpRequestMethodNotSupportedException(Exception e){
        e.printStackTrace(); // 打印到控制台
        return ResultData.fail(HttpStatus.REQUEST_METHOD_NOT_EXIST.getCode(), HttpStatus.REQUEST_METHOD_NOT_EXIST.getMessage());
    }

    //拦截所有异常信息
    @ExceptionHandler(Exception.class)
    public ResultData doException(Exception e){
        e.printStackTrace(); // 打印到控制台
        return ResultData.fail(HttpStatus.FAIL.getCode(), HttpStatus.FAIL.getMessage());
    }
}
