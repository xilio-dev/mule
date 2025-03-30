package com.stackoak.stackoak.common.data.faq;

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
 * FAQ分类表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-31 00:28:42
 */
@Getter
@Setter
@ToString
@TableName("faq_category")
public class FaqCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增ID
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 分类名称，如“账户管理”
     */
    @TableField("name")
    private String name;

    /**
     * 分类描述
     */
    @TableField("description")
    private String description;

    /**
     * 排序顺序，越大越靠前
     */
    @TableField("sort")
    private Integer sort;

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

    /**
     * 创建人ID
     */
    @TableField("created_by")
    private String createdBy;

    /**
     * 更新人ID
     */
    @TableField("updated_by")
    private String updatedBy;
}
