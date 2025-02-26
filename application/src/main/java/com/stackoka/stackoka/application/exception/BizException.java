package com.stackoka.stackoka.application.exception;


import com.stackoka.stackoka.common.message.ResultEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 */
@Getter
@Setter
@ToString
public class BizException extends RuntimeException {
    private int code;
    private String msg;

    public BizException(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public BizException(ResultEnum result) {
        this.code = result.getCode();
        this.msg = result.getMsg();
    }

    public BizException(String message) {
        this.code = ResultEnum.BIZ_ERROR.getCode();
        this.msg = message;
    }
}
