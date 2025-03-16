package com.stackoak.stackoak.common.data.article;

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
 * 文章表
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@Getter
@Setter
@ToString
@TableName("article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 封面
     */
    @TableField("cover")
    private String cover;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 被点赞数量
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 被浏览数量
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 被评论数量
     */
    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 区域
     */
    @TableField("zone")
    private Byte zone;

    /**
     * 被收藏数量
     */
    @TableField("collect_count")
    private Integer collectCount;

    /**
     * 内容字数
     */
    @TableField("content_count")
    private Integer contentCount;

    /**
     * 作者ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 作者ID
     */
    @TableField("category_id")
    private String categoryId;

    /**
     * 可见状态
     */
    @TableField("visible_status")
    private Integer visibleStatus;
    /**
     * 发布状态
     */
    @TableField("publish_status")
    private Integer publishStatus;
    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    private Integer deleted;

    /**
     * 分类专栏
     */
    @TableField("column_id")
    private String columnId;
    /**
     * 创作类型：1原创；2转载；3翻译
     */
    @TableField("creative_type")
    private Integer creativeType;

    /**
     *
     */
    @TableField("visit_password")
    private String visitPassword;

    /**
     *
     */
    @TableField("original_url")
    private String originalUrl;
    /**
     * 状态： 0-审核中、1-已发布、2-仅我可见、3-密码可见、4-粉丝可见、5-草稿箱、6-回收站
     */
    @TableField("status")
    private Integer status;

    /**
     * 转载文章是否被授权
     */
    @TableField("authorize_status")
    private Integer authorizeStatus;

}
