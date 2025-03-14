package com.stackoak.stackoak.common.data.user;

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
 * 用户行为记录表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-14 13:26:02
 */
@Getter
@Setter
@ToString
@TableName("user_behavior")
public class UserBehavior implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 博客ID
     */
    @TableField("article_id")
    private String articleId;

    /**
     * 行为类型
     */
    @TableField("behavior_type")
    private String behaviorType;

    /**
     * 行为时间（精确到毫秒）
     */
    @TableField("behavior_time")
    private LocalDateTime behaviorTime;

    /**
     * 评论ID（需关联comments表，仅当行为类型为COMMENT时有效）
     */
    @TableField("comment_id")
    private String commentId;

    /**
     * 行为来源
     */
    @TableField("referrer")
    private String referrer;

    /**
     * 会话ID
     */
    @TableField("session_id")
    private String sessionId;
}
