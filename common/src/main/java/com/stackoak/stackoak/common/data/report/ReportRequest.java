package com.stackoak.stackoak.common.data.report;

// 举报请求DTO
public record ReportRequest(Integer targetType,
                            String targetId,
                            Integer reasonType,
                            String reasonText) {

}
