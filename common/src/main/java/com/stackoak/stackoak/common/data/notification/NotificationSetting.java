package com.stackoak.stackoak.common.data.notification;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 用户通知设置表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-08 15:44:39
 */
@Getter
@Setter
@ToString
@TableName("notification_setting")
public class NotificationSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    public NotificationSetting(String userId) {
        this.userId = userId;
    }

    /**
     * 用户ID，外键关联用户表
     */
    @TableId(value = "user_id",type = IdType.ASSIGN_UUID)
    private String userId;

    /**
     * 是否开启点赞收藏通知
     */
    @TableField("like_collect_enabled")
    private Boolean likeCollectEnabled;

    /**
     * 是否开启关注通知
     */
    @TableField("follow_enabled")
    private Boolean followEnabled;

    /**
     * 是否开启评论通知
     */
    @TableField("comment_enabled")
    private Boolean commentEnabled;

    /**
     * 是否开启邮件通知
     */
    @TableField("email_enabled")
    private Boolean emailEnabled;

    /**
     * 是否开启短信通知
     */
    @TableField("sms_enabled")
    private Boolean smsEnabled;

    /**
     * 是否开启应用内通知:全局开关
     */
    @TableField("app_enabled")
    private Boolean appEnabled;

    /**
     * 私信通知开启状态
     */
    @TableField("chat_enabled")
    private Boolean chatEnabled;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
