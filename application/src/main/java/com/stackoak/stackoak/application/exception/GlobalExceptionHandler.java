package com.stackoak.stackoak.application.exception;


import com.stackoak.stackoak.common.message.Result;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    // 处理所有其他Exception异常
    @ExceptionHandler(Exception.class)
    public Result handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return com.stackoak.stackoak.common.message.Result.error(500,ex.getMessage());
    }
    // 处理自定义BizException异常
    @ExceptionHandler(BizException.class)
    public Result handleCustomException(BizException ex) {
        ex.printStackTrace();
        return  com.stackoak.stackoak.common.message.Result.error(ex.getCode(),ex.getMsg());
    }
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e){
        e.printStackTrace();
        return Result.error(HttpStatus.BAD_REQUEST.value(),e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        e.printStackTrace();
        return Result.error(HttpStatus.BAD_REQUEST.value(),e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
