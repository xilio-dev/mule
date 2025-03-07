package com.stackoak.stackoak.common.data.notification;

import lombok.Data;

@Data
public class LikeMessage extends Message{
    /**
     * 获取消息唯一ID
     *
     * @return 消息ID
     */
    @Override
    public String getMessageId() {
        return "";
    }

    /**
     * 消息类型
     *
     * @return
     */
    @Override
    public MessageType getType() {
        return null;
    }
}
