package com.stackoak.stackoak.common.data.article;

import lombok.Getter;

@Getter
public enum ArticleStatus {
    NORMAL(1, "正常"),
    RECYCLE(2, "回收站"),
    DRAFT(3, "草稿箱");
    ArticleStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;
    private final String msg;
}
