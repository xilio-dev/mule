package com.stackoak.stackoak.common.data.follow;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 *
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 14:53:49
 */
@Getter
@Setter
@ToString
@TableName("follows")
@NoArgsConstructor
public class Follows implements Serializable {

    private static final long serialVersionUID = 1L;

    public Follows(String userId, String targetUserId) {
        this.userId = userId;
        this.targetUserId = targetUserId;
    }

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("target_user_id")
    private String targetUserId;

    /**
     * 关注或被关注时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
