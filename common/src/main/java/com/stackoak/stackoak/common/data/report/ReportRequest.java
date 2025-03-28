package com.stackoak.stackoak.common.data.report;

// 举报请求DTO
public record ReportRequest(ReportTargetType targetType,
                            String targetId,
                            ReportReasonType reasonType,
                            String reasonText) {

}
