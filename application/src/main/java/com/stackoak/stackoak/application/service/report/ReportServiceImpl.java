package com.stackoak.stackoak.application.service.report;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.report.Report;
import com.stackoak.stackoak.common.data.report.ReportRequest;
import com.stackoak.stackoak.common.data.report.ReportResponse;
import com.stackoak.stackoak.common.data.report.ReportStatus;
import com.stackoak.stackoak.repository.report.ReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public void createReport(ReportRequest request, String userId) {
        LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Report::getTargetId,request.targetId())
                .eq(Report::getTargetType,request.targetType())
                .eq(Report::getUserId,userId);
        getOneOpt(wrapper).ifPresentOrElse(report -> {
            throw new BizException("您已经举报过该内容了");
        }, () -> {
            Report report = new Report();
            report.setTargetId(request.targetId());
            report.setTargetType(request.targetType());
            report.setReasonText(request.reasonText());
            report.setStatus(ReportStatus.PENDING.getCode());/*待处理状态*/
            report.setUserId(userId);/*举报人*/
            report.setReasonType(request.reasonType());
            save(report);
        });
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
