package com.stackoak.stackoak.application.service.notification;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.notification.Notification;

import java.util.Map;

/**
 * <p>
 * 通知表，存储用户的通知信息 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-05 16:20:53
 */
public interface INotificationsService extends IService<Notification> {

    Map<String, Object>  findUserUnReadCount();

}
