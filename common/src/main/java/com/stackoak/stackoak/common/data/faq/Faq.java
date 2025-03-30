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
 * FAQ问题表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-31 00:28:42
 */
@Getter
@Setter
@ToString
@TableName("faq")
public class Faq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增ID
     */
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 所属分类ID
     */
    @TableField("cid")
    private String cid;

    /**
     * 问题标题
     */
    @TableField("question")
    private String question;

    /**
     * 答案内容
     */
    @TableField("answer")
    private String answer;

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
