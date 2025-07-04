package com.stackoak.stackoak.application.controller.portal;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.column.IColumnService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.column.*;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "专栏")
@RestController
@RequestMapping("/column")
public class PortalColumnApi {
    @Autowired
    private IColumnService columnService;

    @PostMapping("list")
    public Result getUserColumns(@RequestBody PageQuery query) {
        String userId = StpKit.USER.getLoginIdAsString();
        return Result.success(columnService.getUserColumns(query,userId));
    }

    @PostMapping("lists")
    public Result getAuthorColumns(@RequestBody ColumnQuery query) {
        return Result.success(columnService.getUserColumns(query,query.getUserId()));
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
    @PostMapping(value = "subscribe", name = "订阅专栏")
    public Result subscribe(@RequestBody SubscribeRequest request) {
        String userId = StpKit.USER.getLoginIdAsString();
        columnService.subscribe(request,userId);
        return Result.success();
    }
    @PutMapping(value = "cancel_subscribe", name = "取消订阅")
    public Result cancelSubscribe (@RequestBody SubscribeRequest request) {
        String userId = StpKit.USER.getLoginIdAsString();
        columnService.cancelSubscribe(request,userId);
        return Result.success();
    }

    @DeleteMapping(value = "del", name = "删除专栏")
    public Result del(@RequestParam String columnId) {
        columnService.deleteColumn(columnId);
        return Result.success();
    }

    @PostMapping(value = "save", name = "新增或更新专栏")
    public Result save(@RequestBody ColumnSaveRequest body) {
        columnService.addOrUpdate(body);
        return Result.success();
    }

    @GetMapping(value = "detail", name = "专栏详情")
    public Result detail(@RequestParam String  cid) {
        return Result.success(columnService.detail(cid));
    }
}
