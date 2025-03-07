package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.service.notification.SseClient;
import com.stackoak.stackoak.common.message.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * <p>
 * 通知表，存储用户的通知信息 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-05 16:20:53
 */
@RestController
@RequestMapping("/notification")
public class NotificationApi {
    @Autowired
    private SseClient sseClient;

    @GetMapping("/createSse/{uid}")
    public SseEmitter createSseSession(@PathVariable String uid) {
        return sseClient.createSse(uid);
    }

    @GetMapping("/closeSse/{uid}")
    public RestResult closeSseSession(@PathVariable String uid) {
        sseClient.closeSse(uid);
        return RestResult.success("sse session closed");
    }
    @GetMapping("/sendMsg/{uid}/{msg}")
    public RestResult sendMsg(@PathVariable String uid,@PathVariable String msg) {
       // sseClient.sendMessage(uid,msg);
        return RestResult.success("sse session closed");
    }
}
