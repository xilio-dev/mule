package com.stackoak.stackoak.common.data.article;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.stackoak.stackoak.common.data.column.Column;
import com.stackoak.stackoak.common.data.tag.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.stackoak.stackoak.common.data.user.UserConfig;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDetailVO implements Serializable {
    // 文章信息
    private ArticleInfoDTO articleInfo;
    // 用户信息
    private UserInfoDTO userInfo;
    // 标签信息
    private List<Tag> tags;
    // 用户互动信息
    private UserInteractDTO userInteract;
    // 分类信息
    private CategoryInfoDTO category;
    // 标签ID
    private String tagIds;
    // 标签名称
    private String tagNames;
    private List<Column> columns;
    private String columnIds;
    private String columnNames;
}



@Data
class ArticleInfoDTO {
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


@Data
// 定义一个CategoryInfoDTO类，用于存储分类信息
class CategoryInfoDTO {
    // 分类ID
    private String categoryId;
    // 分类名称
    private String categoryName;
}

