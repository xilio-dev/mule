package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.report.IReportService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.report.ReportRequest;
import com.stackoak.stackoak.common.data.report.ReportResponse;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-28 21:31:20
 */
@RestController
@RequestMapping("/report")
public class PortalReportController {
    @Autowired
    private IReportService reportService;

    // 提交举报
    @PostMapping("createReport")
    public Result createReport(@RequestBody ReportRequest request) {
        String userId = StpKit.USER.getLoginIdAsString();
        ReportResponse response = reportService.createReport(request,userId);
        return Result.success(response);
    }


    // 获取用户自己的举报记录
    @GetMapping("/user")
    public  Result getUserReports(@RequestBody PageQuery pageQuery) {
        String userId = StpKit.USER.getLoginIdAsString();
        Page<ReportResponse> reports = reportService.getUserReports( pageQuery,userId);
        return  Result.success(reports);
    }

}
