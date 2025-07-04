package com.stackoak.stackoak.common.data.user;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserDTO implements Serializable {
    private String id;
    private String nickname;
    private String avatar;
    private String description;
}
