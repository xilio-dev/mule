package com.stackoka.stackoka.application.exception;


import com.stackoka.stackoka.common.message.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    // 处理所有其他Exception异常
    @ExceptionHandler(Exception.class)
    public Result handleAllExceptions(Exception ex) {
        return Result.error(500,ex.getMessage());
    }
    // 处理自定义BizException异常
    @ExceptionHandler(BizException.class)
    public Result handleCustomException(BizException ex) {
        return  Result.error(ex.getCode(),ex.getMsg());
    }
}
