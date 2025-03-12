package com.stackoak.stackoak.common.data.notification;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Message {
    private String messageId;
    private Integer type;

    /**
     * 获取消息唯一ID
     * @return 消息ID
     */
    public abstract String getMessageId();

    /**
     * 消息类型
     * @return
     */
    public abstract MessageType getType();
}
