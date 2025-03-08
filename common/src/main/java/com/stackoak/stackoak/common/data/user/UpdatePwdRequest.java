package com.stackoak.stackoak.common.data.user;

import lombok.Data;

import java.io.Serializable;
@Data
public class UpdatePwdRequest implements Serializable {
    private String newPassword;
    private String code;
}
