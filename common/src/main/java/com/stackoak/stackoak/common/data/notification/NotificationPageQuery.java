package com.stackoak.stackoak.common.data.notification;

import com.stackoak.stackoak.common.data.PageQuery;

/**
 * 消息分页查询
 * @param pageQuery 分页参数
 * @param type 消息的类型
 */
public record NotificationPageQuery(PageQuery pageQuery, Integer type) {
}
