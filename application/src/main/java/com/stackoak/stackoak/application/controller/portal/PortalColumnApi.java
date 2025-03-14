package com.stackoak.stackoak.application.controller.portal;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stackoak.stackoak.application.service.column.IColumnService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.column.Column;
import com.stackoak.stackoak.common.data.column.ColumnQuery;
import com.stackoak.stackoak.common.data.column.ColumnSaveRequest;
import com.stackoak.stackoak.common.data.column.ListColumnByUserQuery;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-23
 */
@RestController
@RequestMapping("/column")
public class PortalColumnApi {
    @Autowired
    private IColumnService columnService;

    @PostMapping("list")
    public Result getUserColumns(@RequestBody ColumnQuery query) {
        return Result.success(columnService.getUserColumns(query));
    }

    @PostMapping("lists")
    public Result getAuthorColumns(@RequestBody ColumnQuery query) {
        return Result.success(columnService.getUserColumns(query));
    }

    @PostMapping("list_by_user")
    public Result listByUser(@RequestBody ListColumnByUserQuery query) {
        return Result.success(columnService.listByUser(query));
    }

    @PostMapping(value = "subscribe-to-me", name = "订阅我的专栏")
    public Result getSubscribeToMe(@RequestBody CommonPageQuery query) {
        return Result.success(columnService.subscribeToMe(query));
    }

    @PostMapping(value = "subscribe-from-me", name = "我订阅的专栏")
    public Result getSubscribeFromMe(@RequestBody CommonPageQuery query) {
        return Result.success(columnService.subscribeFromMe(query));
    }

    @DeleteMapping(value = "del", name = "删除专栏")
    public Result del(@RequestParam String column_id) {
        columnService.deleteColumn(column_id);
        return Result.success();
    }
    @DeleteMapping(value = "save", name = "新增或更新专栏")
    public Result save(@RequestBody ColumnSaveRequest body) {
        columnService.addOrUpdate(body);
        return Result.success();
    }




}
