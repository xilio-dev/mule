package com.stackoak.stackoak.application.service.report;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.report.Report;
import com.stackoak.stackoak.common.data.report.ReportRequest;
import com.stackoak.stackoak.common.data.report.ReportResponse;
import com.stackoak.stackoak.common.data.report.ReportStatus;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-28 21:31:20
 */
public interface IReportService extends IService<Report> {
    // 提交举报
    void createReport(ReportRequest request, String userId);

    // 处理举报（管理员）
    void handleReport(String reportId, String handlerId, ReportStatus status, String handleRemark);

    // 查询用户举报列表
    Page<ReportResponse> getUserReports(PageQuery pageQuery, String userId);

    // 查询待处理举报（管理员）
    Page<ReportResponse> getPendingReports(PageQuery pageQuery);

}
