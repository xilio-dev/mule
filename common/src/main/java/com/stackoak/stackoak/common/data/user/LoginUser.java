package com.stackoak.stackoak.common.data.user;

import lombok.Data;

@Data
public class LoginUser {
    private String userId;
    private String nickname;
    private String email;
    private String avatar;
    private String description;
}
