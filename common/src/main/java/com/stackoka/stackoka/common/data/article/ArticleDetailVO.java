package com.stackoka.stackoka.common.data.article;

import com.stackoka.stackoka.common.data.tag.TagInfoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDetailVO implements Serializable {
    private ArticleInfoDTO articleInfo;
    private UserInfoDTO userInfo;
    private List<TagInfoDTO> tags;
    private UserInteract userInteract;
    private CategoryInfoDTO category;
    private String tagIds;
    private String tagNames;
}

@Data
class UserInfoDTO {
    private Long userId;
    private String userName;
    private String avatar;

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
     *
     */
    private Integer visibleStatus;
    /**
     *
     */
    private Integer publishStatus;
    /**
     *
     */
    private Long columnId;
    /**
     *
     */
    private Integer creativeType;
    /**
     *
     */
    private String originalUrl;

}


@Data
class CategoryInfoDTO {
private Long categoryId;
private String categoryName;
}
@Data
class UserInteract {
    private Long id;
    private Long userId;
    private Boolean isLike;
    private Boolean isFollow;
    private Boolean isCollect;
}

