package com.example.springboot.exception;

import com.example.springboot.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler(Exception.class)
    public Result ex(Exception e){
        e.printStackTrace();
        return Result.error("服务器出现错误，请联系管理员");

    }
}
