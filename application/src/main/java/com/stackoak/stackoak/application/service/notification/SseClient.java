package com.stackoak.stackoak.application.service.notification;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseClient {
    private static final Logger log = LoggerFactory.getLogger(SseClient.class);
    private static final ConcurrentHashMap<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>(256);
    private static final int RECONNECT_DELAY = 5000;

    public SseEmitter createSse(String uid) {
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitterMap.put(uid, sseEmitter);
        sseEmitter.onCompletion(() -> {
            log.info("[{}] 连接结束", uid);
            sseEmitterMap.remove(uid);
        });
        sseEmitter.onTimeout(() -> {
            log.info("[{}] 连接超时，准备重连", uid);
            scheduleReconnect(uid);
        });
        sseEmitter.onError(throwable -> {
            log.error("[{}] 连接异常: {}", uid, throwable.getMessage(), throwable);
            scheduleReconnect(uid);
        });
        return sseEmitter;
    }

    public boolean sendMessage(String uid, Object message) {
        SseEmitter sseEmitter = sseEmitterMap.get(uid);
        if (sseEmitter == null) {
            log.warn("消息推送失败，未找到 UID: {}", uid);
            return false;
        }
        try {
            String json = new Gson().toJson(message);
            sseEmitter.send(SseEmitter.event().data(json));
            return true;
        } catch (IOException e) {
            log.error("消息推送异常: {}", e.getMessage(), e);
            return false;
        }
    }

    public void closeSse(String uid) {
        SseEmitter sseEmitter = sseEmitterMap.remove(uid);
        if (sseEmitter != null) {
            sseEmitter.complete();
            log.info("关闭 UID: {} 的连接", uid);
        }
    }

    private void scheduleReconnect(String uid) {
        log.info("[{}] 计划在 {} 毫秒后重连", uid, RECONNECT_DELAY);
        new Thread(() -> {
            try {
                Thread.sleep(RECONNECT_DELAY);
                createSse(uid);
            } catch (InterruptedException e) {
                log.error("[{}] 重连操作被中断", uid, e);
            }
        }).start();
    }
}
