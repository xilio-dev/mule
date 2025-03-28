package com.stackoak.stackoak.common.data.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 举报状态枚举
public enum ReportStatus {
    PENDING(0, "待处理"),
    PROCESSED(1, "已处理"),
    REJECTED(2, "已拒绝");

    private final int code;
    private final String desc;
}
