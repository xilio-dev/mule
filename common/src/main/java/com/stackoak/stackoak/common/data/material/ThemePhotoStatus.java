package com.stackoak.stackoak.common.data.material;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ThemePhotoStatus {
    NORMAL(1, "正常"),
    DISABLED(2, "禁用");
    private final int code;
    private final String desc;
}
