package com.stackoka.stackoka.common.data.likes;

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
 *
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 22:02:39
 */
@Getter
@Setter
@ToString
@TableName("likes")
public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户
     */
    @TableField("user_id")
    private String userId;

    /**
     * 点赞类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 点赞的目标
     */
    @TableField("target_id")
    private String targetId;

    @TableField("created_at")
    private LocalDateTime createdAt;
}
