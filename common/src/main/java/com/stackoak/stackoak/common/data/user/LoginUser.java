package com.stackoak.stackoak.common.data.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {
    private String userId;
    private String nickname;
    private String email;
    private String avatar;
    private String description;
}
