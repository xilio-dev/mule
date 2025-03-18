package com.stackoak.stackoak.common.data.notification;

/**
 * 设置所有消息已读
 *
 * @param latestId    最后一个消息ID
 * @param type 消息类型
 */
public record SetAllReadRequest(String latestId, Integer type) {
}
