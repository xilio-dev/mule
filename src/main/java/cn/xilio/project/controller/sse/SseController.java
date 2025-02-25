package cn.xilio.project.controller.sse;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {
    private final SseClient sseClient;

    public SseController(SseClient sseClient) {
        this.sseClient = sseClient;
    }

    @GetMapping("/createSse/{uid}")
    public SseEmitter createSse(@PathVariable String uid) {
        return sseClient.createSse(uid);
    }

    @GetMapping("/sendMessage/{uid}")
    public String sendMessage(@PathVariable String uid, @RequestParam String message) {
        boolean success = sseClient.sendMessage(uid, message);
        return success ? "消息发送成功" : "消息发送失败";
    }

    @GetMapping("/closeSse/{uid}")
    public String closeSse(@PathVariable String uid) {
        sseClient.closeSse(uid);
        return "连接已关闭";
    }
}
