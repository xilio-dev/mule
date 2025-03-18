package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.service.notification.INotificationsService;
import com.stackoak.stackoak.application.service.notification.SseClient;
import com.stackoak.stackoak.common.data.notification.Notification;
import com.stackoak.stackoak.common.data.notification.NotificationPageQuery;
import com.stackoak.stackoak.common.data.notification.SetAllReadRequest;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通知表，存储用户的通知信息 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-05 16:20:53
 */
@Tag(name = "通知")
@RestController
@RequestMapping("/notification")
public class PortalNotificationApi {
    @Autowired
    private SseClient sseClient;
    @Autowired
    private INotificationsService ns;

    @GetMapping("/createSse/{uid}")
    public SseEmitter createSseSession(@PathVariable String uid) {
        return sseClient.createSse(uid);
    }

    @GetMapping("/closeSse/{uid}")
    public Result closeSseSession(@PathVariable String uid) {
        sseClient.closeSse(uid);
        return Result.success("sse session closed");
    }

    @PostMapping(value = "get_message", name = "获取消息")
    public Result getMessage(@RequestBody NotificationPageQuery request) {
        Page<Notification> messages = ns.getMessageByType(request);
        return Result.success(messages);
    }

    @GetMapping(value = "unread_count", name = "获取未阅读消息数量信息")
    public Result unReadCount() {
        return Result.success(ns.findUserUnReadCount());
    }

    @PostMapping(value = "set_all_read", name = "标记所有消息为已读状态")
    public Result setAllRead(@RequestBody SetAllReadRequest request) {
        ns.setAllRead(request);
        //设置以后再获取一下维度消息
        return Result.success();
    }
}
