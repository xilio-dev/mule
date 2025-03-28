package com.stackoak.stackoak.common.data.report;

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
 * @since 2025-03-28 21:31:20
 */
@Getter
@Setter
@ToString
@TableName("report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("target_type")
    private Integer targetType;

    @TableField("target_id")
    private String targetId;

    /**
     * 举报目标类型(1:文章 2:评论)
     */
    @TableField("reason_type")
    private Integer reasonType;

    /**
     * 举报原因类型(1:色情 2:暴力 3:广告 4:其他)
     */
    @TableField("reason_text")
    private String reasonText;

    /**
     * 处理状态(0:待处理 1:已处理 2:已拒绝)
     */
    @TableField("status")
    private Integer status;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @TableField("handler_id")
    private String handlerId;

    @TableField("handle_remark")
    private String handleRemark;
}
