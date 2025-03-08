package com.stackoak.stackoak.common.data.notification;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MessageItem implements Serializable {
    /**
     * 推送的目标作者ID
     */
    private String userId;
    /**
     * 目标作者用户名
     */
    private String username;
    private Integer status;
    /**
     * 通知创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 通知ID
     */
    private String id;
    /**
     * 通知的内容
     */
    private MessageContent content;
}
