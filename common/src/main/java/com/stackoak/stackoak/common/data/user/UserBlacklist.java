package com.stackoak.stackoak.common.data.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * 用户黑名单表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-31 00:11:51
 */
@Getter
@Setter
@ToString
@TableName("user_blacklist")
public class UserBlacklist implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 被拉黑的用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 执行拉黑的用户
     */
    @TableField("blocked_by")
    private String blockedBy;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
