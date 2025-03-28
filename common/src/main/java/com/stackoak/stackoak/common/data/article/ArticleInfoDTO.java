package com.stackoak.stackoak.common.data.article;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleInfoDTO {
    /**
     * 编号
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 封面
     */
    private String cover;

    /**
     * 描述
     */
    private String description;

    /**
     * 被点赞数量
     */
    private Integer likeCount;

    /**
     * 被浏览数量
     */
    private Integer viewCount;

    /**
     * 被评论数量
     */
    private Integer commentCount;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime publishTime;


    /**
     * 被收藏数量
     */
    private Integer collectCount;

    /**
     * 内容字数
     */
    private Integer contentCount;
    /**
     * 可见状态
     */
    private Integer visibleStatus;
    /**
     * 发布状态
     */
    private Integer publishStatus;
    /**
     * 栏目ID
     */
    private Long columnId;
    /**
     * 文章类型
     */
    private Integer creativeType;
    /**
     * 原始URL
     */
    private String originalUrl;
    /**
     * 访问密码
     */
    private String visitPassword;

    /**
     * 授权状态
     */
    private Integer authorizeStatus;

}
