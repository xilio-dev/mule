package com.stackoak.stackoak.common.data.material;

import lombok.Getter;

@Getter
public enum MaterialStatus {
    NORMAL(1, "正常"),
    AUDIT(2, "审核中"),
    FAIL_AUDIT(0, "审核未通过");

    MaterialStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;
}
