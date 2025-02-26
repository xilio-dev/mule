package com.stackoka.stackoka.application.controller;


import com.stackoka.stackoka.common.message.Result;
import com.stackoka.stackoka.application.service.column.IColumnService;
import com.stackoka.stackoka.common.data.column.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-23
 */
@RestController
@RequestMapping("column")
public class ColumnController {
    @Autowired
    private IColumnService columnService;

    @GetMapping("list")
    public Result list() {
        List<Column> list = columnService.list();
        return Result.success(list);
    }

}
