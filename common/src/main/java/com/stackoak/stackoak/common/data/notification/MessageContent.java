package com.stackoak.stackoak.common.data.notification;

import lombok.Data;

import java.io.Serializable;
@Data
public class MessageContent implements Serializable {
    private String tt; // 模板字符串
    private String pd; // 类型（如博客）
    private String avatarUrl; // 发送者用户头像 URL
    private String nickname; // 用户昵称
    private boolean isFans; // 是否是粉丝
    private String id; // 文章的 ID
    private String title; // 文章标题
    private String url; // 文章链接
    private String username; // 发送消息者用户名
}
