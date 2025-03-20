package com.stackoak.stackoak.common.data.auth;

import lombok.Data;

import java.io.Serializable;
@Data
public class QrCodeDTO implements Serializable {
    private String imgUrl;
    private String sign;
}
