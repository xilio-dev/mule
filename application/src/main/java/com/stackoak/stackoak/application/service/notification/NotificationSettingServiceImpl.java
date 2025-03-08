package com.stackoak.stackoak.application.service.notification;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.notification.NotificationSetting;
import com.stackoak.stackoak.repository.notification.NotificationSettingMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户通知设置表 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-08 15:44:39
 */
@Service
public class NotificationSettingServiceImpl extends ServiceImpl<NotificationSettingMapper, NotificationSetting> implements INotificationSettingService {

}
