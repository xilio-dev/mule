package com.stackoak.stackoak.application.service.notification;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.notification.NotificationSetting;
import com.stackoak.stackoak.common.data.notification.SettingNotifyRequest;

/**
 * <p>
 * 用户通知设置表 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-08 15:44:39
 */
public interface INotificationSettingService extends IService<NotificationSetting> {

    void setNotify(SettingNotifyRequest request);

    NotificationSetting getUserNotifySetting();

}
