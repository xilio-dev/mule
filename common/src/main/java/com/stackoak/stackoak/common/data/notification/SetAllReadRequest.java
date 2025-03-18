package com.stackoak.stackoak.common.data.notification;

/**
 * 设置所有消息已读
 *
 * @param latestId    最后一个消息ID
 * @param messageType 消息内心
 */
public record SetAllReadRequest(String latestId, Integer messageType) {
}
