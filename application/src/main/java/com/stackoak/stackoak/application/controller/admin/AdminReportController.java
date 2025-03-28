package com.stackoak.stackoak.application.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.report.IReportService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.report.ReportRequest;
import com.stackoak.stackoak.common.data.report.ReportResponse;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class AdminReportController {
    @Autowired
    private IReportService reportService;


}
