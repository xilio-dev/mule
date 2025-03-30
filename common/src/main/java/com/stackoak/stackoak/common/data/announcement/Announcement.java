package com.stackoak.stackoak.common.data.announcement;

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
 * @since 2025-03-30 22:06:08
 */
@Getter
@Setter
@ToString
@TableName("announcement")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * '1-SYSTEM', '2-EVENT', '3-POLICY', '4-COMMUNITY', '5-EMERGENCY'
     */
    @TableField("type")
    private Integer type;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    @TableField("is_published")
    private Boolean isPublished;
}
