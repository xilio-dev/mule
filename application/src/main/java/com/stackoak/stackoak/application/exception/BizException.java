package com.stackoak.stackoak.application.exception;


import com.stackoak.stackoak.common.message.ResultEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

    public static void noEmpty(String value,String msg) {
        if (!StringUtils.hasText(value)){
            throw new BizException(msg);
        }
    }
    public static void noEmpty(String value,ResultEnum resultEnum) {
        if (!StringUtils.hasText(value)){
            throw new BizException(resultEnum);
        }
    }
    public static void noNull(Object value,String msg) {
        if (ObjectUtils.isEmpty(value)){
            throw new BizException(msg);
        }
    }
    public static void exprNull(boolean expr,String msg) {
        if (expr){
            throw new BizException(msg);
        }
    }
    public static void exprNull(boolean expr,ResultEnum resultEnum) {
        if (expr){
            throw new BizException(resultEnum);
        }
    }

}
