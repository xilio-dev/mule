package com.stackoak.stackoak.application.service.notification;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.common.data.notification.NotificationSetting;
import com.stackoak.stackoak.common.data.notification.NotificationType;
import com.stackoak.stackoak.common.data.notification.SettingNotifyRequest;
import com.stackoak.stackoak.repository.notification.NotificationSettingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户通知设置表 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-08 15:44:39
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NotificationSettingServiceImpl extends ServiceImpl<NotificationSettingMapper, NotificationSetting> implements INotificationSettingService {

    @Override
    public void setNotify(SettingNotifyRequest request) {
        String currentUserId = StpKit.USER.getLoginIdAsString();
        Boolean enabled = request.getEnabled();
        NotificationSetting setting = new NotificationSetting(currentUserId);
        NotificationType notificationType = NotificationType.getTypeByValue(request.getType());
        switch (notificationType) {
            case COMMENT:
                setting.setCommentEnabled(enabled);
                break;
            case LIKE_COLLECT:
                setting.setLikeCollectEnabled(enabled);
                break;
            case FOLLOW:
                setting.setFollowEnabled(enabled);
                break;
            case CHAT:
                setting.setChatEnabled(enabled);
                break;
        }
        //更新用户通知的配置
        updateById(setting);
    }

    @Override
    public NotificationSetting getUserNotifySetting() {
        return getById(StpKit.USER.getLoginIdAsString());
    }
}
