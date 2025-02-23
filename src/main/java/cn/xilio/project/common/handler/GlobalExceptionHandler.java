package cn.xilio.project.common.handler;

import cn.xilio.project.common.Result;
import cn.xilio.project.common.ex.BizException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
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
