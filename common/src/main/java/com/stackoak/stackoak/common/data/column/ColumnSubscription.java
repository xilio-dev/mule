package com.stackoak.stackoak.common.data.column;

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
 * 专栏订阅表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-14 22:22:44
 */
@Getter
@Setter
@ToString
@TableName("column_subscription")
public class ColumnSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 专栏ID
     */
    @TableField("column_id")
    private String columnId;

    /**
     * 订阅用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 订阅时间
     */
    @TableField("subscribed_at")
    private LocalDateTime subscribedAt;

    /**
     * 订阅状态（1=正常，0=取消）
     */
    @TableField("status")
    private Integer status;
}
