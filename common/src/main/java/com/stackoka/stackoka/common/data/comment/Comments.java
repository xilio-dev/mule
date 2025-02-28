package com.stackoka.stackoka.common.data.comment;

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
 * 评论表，存储用户对文章的评论
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
@Getter
@Setter
@ToString
@TableName("comments")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID，主键
     */
    @TableId(value = "id" ,type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 文章ID，逻辑关联posts表
     */
    @TableField("article_id")
    private String articleId;

    /**
     * 评论者ID，逻辑关联users表
     */
    @TableField("user_id")
    private String userId;

    /**
     * 父评论ID，用于支持评论的回复功能
     */
    @TableField("pid")
    private String pid;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 评论创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 评论更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
    /**
     * 点赞数量
     */
    @TableField("like_count")
    private Integer likeCount;
}
