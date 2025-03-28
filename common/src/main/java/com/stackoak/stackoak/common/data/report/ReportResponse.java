package com.stackoak.stackoak.common.data.report;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @param reportId
 * @param status
 * @param createdAt
 */
public record ReportResponse(String reportId, Integer status, LocalDateTime createdAt) {
}
