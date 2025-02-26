package com.stackoka.stackoka.common.data.article;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN("admin", "后台管理员"),
    USER("user", "登陆用户");

    private String type;
    private String desc;

    UserRoleEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
