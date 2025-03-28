package com.stackoak.stackoak.common.data.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 举报类型枚举
public enum ReportTargetType {
    ARTICLE(1, "文章"),
    COMMENT(2, "评论");

    private final int code;
    private final String desc;
}
