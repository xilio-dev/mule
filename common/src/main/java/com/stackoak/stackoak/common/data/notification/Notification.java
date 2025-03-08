package com.stackoak.stackoak.common.data.notification;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import com.baomidou.mybatisplus.extension.handlers.GsonTypeHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 通知表，存储用户的通知信息
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-05 16:20:53
 */
@Getter
@Setter
@ToString
@TableName("notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通知ID，主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 接收通知的用户ID，外键关联users表
     */
    @TableField("user_id")
    private String userId;

    /**
     * 通知类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 通知内容，JSON格式存储详细信息
     */
    @TableField(value = "content", typeHandler = GsonTypeHandler.class)
    private Map<String, Object> content;

    /**
     * 是否已读，默认未读
     */
    @TableField("is_read")
    private Boolean isRead;

    /**
     * 通知创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
