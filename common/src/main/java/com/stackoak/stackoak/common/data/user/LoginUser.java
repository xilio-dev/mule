package com.stackoak.stackoak.common.data.user;

import lombok.Data;

@Data
public class LoginUser {
    private String userId;
    private String username;
    private String avatar;
    private String description;
}
