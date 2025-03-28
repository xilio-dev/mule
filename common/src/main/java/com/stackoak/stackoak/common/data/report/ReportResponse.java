package com.stackoak.stackoak.common.data.report;

import java.time.LocalDate;

/**
 *
 * @param reportId
 * @param status
 * @param createdAt
 */
public record ReportResponse(String reportId, ReportStatus status, LocalDate createdAt) {
}
