package com.stackoak.stackoak.application.service.notification;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.notification.Notification;
import com.stackoak.stackoak.common.data.notification.NotificationPageQuery;
import com.stackoak.stackoak.common.data.notification.SetAllReadRequest;
import com.stackoak.stackoak.common.data.notification.TypeCount;
import com.stackoak.stackoak.repository.notification.NotificationsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setAllRead(SetAllReadRequest request) {
        String userId = StpKit.USER.getLoginIdAsString();
        String mid = request.latestId();
        Integer type = request.type();
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, userId);
        wrapper.eq(Notification::getId, mid);
        wrapper.eq(Notification::getType, type);
        Notification notification = getOne(wrapper);
        if (ObjectUtils.isEmpty(notification)) {
            throw new BizException("没有找到latestId对应消息");
        }
        //获取该消息的创建时间
        LocalDateTime createdAt = notification.getCreatedAt();
        //所有在createdAt创建时间的消息设置为已读状态
        LambdaQueryWrapper<Notification> wrp = new LambdaQueryWrapper<>();
        wrp.eq(Notification::getUserId, userId);
        wrp.le(Notification::getCreatedAt, createdAt);
        wrp.eq(Notification::getType, type);
        wrp.eq(Notification::getIsRead, false);

        Notification updateEntity = new Notification();
        updateEntity.setIsRead(true);
        update(updateEntity, wrp);
    }

    @Override
    public Page<Notification> getMessageByType(NotificationPageQuery request) {
        //todo 临时
        LambdaQueryWrapper<Notification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Notification::getUserId, StpKit.USER.getLoginIdAsString());
        wrapper.eq(Notification::getType, request.type());
        return this.page(Page.of(request.pageQuery().getCurrent(), request.pageQuery().getSize()), wrapper);
    }
}
