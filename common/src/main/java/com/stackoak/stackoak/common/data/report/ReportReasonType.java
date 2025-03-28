package com.stackoak.stackoak.common.data.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 举报原因枚举
public enum ReportReasonType {
    PORN(1, "色情内容"),
    VIOLENCE(2, "暴力内容"),
    AD(3, "广告营销"),
    OTHER(4, "其他");

    private final int code;
    private final String desc;
}
