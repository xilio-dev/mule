package com.stackoak.stackoak.application.service.notification;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.common.data.notification.Notification;
import com.stackoak.stackoak.common.data.notification.TypeCount;
import com.stackoak.stackoak.repository.notification.NotificationsMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;


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

    @Override
    public Map<String, Object> findUserUnReadCount() {
        String userId = StpKit.USER.getLoginIdAsString();
        List<TypeCount> data = baseMapper.selectUserUnReadCount(userId);

        Map<Long, Long> map = data.stream()
                .collect(Collectors.toMap(TypeCount::type, TypeCount::count));
        long total = map.values().stream().mapToLong(Long::longValue).sum();
        Map<String, Object> response = new HashMap<>();
        response.put("count", map);
        response.put("total", total);
        return response;
    }
}
