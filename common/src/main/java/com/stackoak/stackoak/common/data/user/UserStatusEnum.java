package com.stackoak.stackoak.common.data.user;

import lombok.Getter;

@Getter
public enum UserStatusEnum {
    NORMAL(1, "正常"),
    BLOCKED(2, "封禁");

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private Integer status;
    private String desc;
}
