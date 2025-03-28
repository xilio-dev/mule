package com.stackoak.stackoak.common.data.report;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
// 举报原因枚举
public enum ReportReasonType {
    PORN(1, "内容色情"),
    VIOLENCE(2, "过于暴力"),
    DEFAMATION(3, "侵犯名誉"),
    PLAGIARISM(4, "内容抄袭"),
    POLITICS(5, "谈论政治"),
    PORTRAIT_INFRINGEMENT(6, "肖像侵权"),
    COPYRIGHT_INFRINGEMENT(7, "著作侵权"),
    OTHER(8, "其他侵权");

    private final int code;
    private final String desc;
}
