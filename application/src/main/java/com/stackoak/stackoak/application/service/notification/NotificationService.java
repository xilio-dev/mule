package com.stackoak.stackoak.application.service.notification;

import com.stackoak.stackoak.common.message.RestResult;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class NotificationService {
    // 用于存储所有客户端的连接
    private final Set<HttpServletResponse> clients = Collections.synchronizedSet(new HashSet<>());

    // 添加客户端连接
    public void addClient(HttpServletResponse response) {
        clients.add(response);
    }

    // 移除客户端连接
    public void removeClient( HttpServletResponse response) {
        clients.remove(response);
    }

    // 推送通知给所有客户端
    public void sendNotification(String message) {
        clients.forEach(client -> {
            try {
                RestResult result = new RestResult();
                result.put("message", message);
                String json = new Gson().toJson(result);
                client.getWriter().write(json);
                client.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
