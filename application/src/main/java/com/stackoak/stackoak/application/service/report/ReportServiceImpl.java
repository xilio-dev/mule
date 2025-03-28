package com.stackoak.stackoak.application.service.report;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.report.Report;
import com.stackoak.stackoak.common.data.report.ReportRequest;
import com.stackoak.stackoak.common.data.report.ReportResponse;
import com.stackoak.stackoak.common.data.report.ReportStatus;
import com.stackoak.stackoak.repository.report.ReportMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-28 21:31:20
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {

    @Override
    public ReportResponse createReport(ReportRequest request, String userId) {
        return null;
    }

    @Override
    public void handleReport(String reportId, String handlerId, ReportStatus status, String handleRemark) {

    }

    @Override
    public Page<ReportResponse> getUserReports(PageQuery pageQuery, String userId) {
        return null;
    }

    @Override
    public Page<ReportResponse> getPendingReports(PageQuery pageQuery) {
        return null;
    }
}
