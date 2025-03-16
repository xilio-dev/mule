package com.stackoak.stackoak.common.data.article;

import lombok.Getter;


@Getter
public enum ArticleStatus {
    UNDER_REVIEW(0, "审核中"),
    PUBLISHED(1, "已发布"),
    PRIVATE(2, "仅我可见"),
    PASSWORD_PROTECTED(3, "密码可见"),
    FANS_ONLY(4, "粉丝可见"),
    DRAFT(5, "草稿箱"),
    RECYCLE(6, "回收站"),
    REJECTED(7, "未通过审核");

    ArticleStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;
    private final String msg;
}
