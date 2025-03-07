package com.stackoak.stackoak.common.data.notification;

import lombok.Data;
import lombok.Setter;

@Setter
public class CommentMessage extends Message{
    private String content;

    public String getContent() {
        return "用户评论了你的文章："+content;
    }

    /**
     * 获取消息唯一ID
     *
     * @return 消息ID
     */
    @Override
    public String getMessageId() {
        return "1001";
    }

    /**
     * 消息类型
     *
     * @return
     */
    @Override
    public MessageType getType() {
        return MessageType.COMMENT;
    }
}
