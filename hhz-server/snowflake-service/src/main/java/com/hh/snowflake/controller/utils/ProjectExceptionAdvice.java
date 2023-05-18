package com.hh.snowflake.controller.utils;


import com.hh.commons.common.HttpStatus;
import com.hh.commons.common.ResultData;
import com.hh.commons.excption.ClockBackwardsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//作为springmvc的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(ClockBackwardsException.class)
    public ResultData doInvalidTokenException(Exception e){
        e.printStackTrace(); // 打印到控制台
        return ResultData.fail(HttpStatus.TIMEOUT_CREATE_ID.getCode(), HttpStatus.TIMEOUT_CREATE_ID.getMessage());
    }

    //拦截所有异常信息
    @ExceptionHandler(Exception.class)
    public ResultData doException(Exception e){
        e.printStackTrace(); // 打印到控制台
        return ResultData.fail(HttpStatus.FAIL.getCode(), HttpStatus.FAIL.getMessage());
    }




}
