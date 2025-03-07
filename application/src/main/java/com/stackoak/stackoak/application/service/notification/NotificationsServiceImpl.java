package com.stackoak.stackoak.application.service.notification;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.notification.Notification;
import com.stackoak.stackoak.repository.notification.NotificationsMapper;
import org.springframework.stereotype.Service;



/**
 * <p>
 * 通知表，存储用户的通知信息 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-05 16:20:53
 */
@Service
public class NotificationsServiceImpl extends ServiceImpl<NotificationsMapper, Notification> implements INotificationsService {

}
