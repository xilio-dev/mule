package com.stackoak.stackoak.common.data.article;
import com.stackoak.stackoak.common.data.tag.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleBriefVO {
    /**
     * 编号
     */
    private String id;

    /**
     * 标题
     */
    private String title;
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
    private LocalDateTime publishTime;
    /**
     * 被收藏数量
     */
    private Integer collectCount;
    /**
     * 作者ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 标签列表
     */
    private List<Tag> tags;
    /**
     * 标签ID列表
     */
    private String tagIds;
    /**
     * 标签名字列表
     */
    private String tagNames;
    /**
     * 可见状态
     */
    private Integer visibleStatus;
}
