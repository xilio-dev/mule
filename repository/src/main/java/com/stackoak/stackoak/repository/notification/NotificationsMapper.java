package com.stackoak.stackoak.repository.notification;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stackoak.stackoak.common.data.notification.Notification;
import com.stackoak.stackoak.common.data.notification.TypeCount;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 通知表，存储用户的通知信息 Mapper 接口
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-05 16:20:53
 */
public interface NotificationsMapper extends BaseMapper<Notification> {


    List<TypeCount> selectUserUnReadCount(@Param("userId") String userId);

    // 更新满足条件的消息为已读状态
    @Update("UPDATE notification SET is_read = true WHERE user_id = #{userId} AND created_at <= #{createdAt} AND type = #{type} AND is_read = false")
    int updateAllRead(@Param("userId") String userId, @Param("createdAt") LocalDateTime createdAt, @Param("type") Integer type);

}

