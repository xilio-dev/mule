package cn.xilio.project.common.handler;

import cn.xilio.project.common.Result;
import cn.xilio.project.common.ex.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 处理自定义BizException异常
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Object> handleCustomException(BizException ex) {
        return new ResponseEntity<>(Result.error(ex.getCode(),ex.getMsg()), HttpStatus.BAD_REQUEST);
    }

    // 处理所有其他Exception异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(Result.error(500,ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
