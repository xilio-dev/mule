package com.stackoak.stackoak.common.data.material;

import lombok.Getter;

@Getter
public enum MaterialType {
    SYSTEM(0, "系统提供素材"),
    USER(1, "用户上传");

    MaterialType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;
}
