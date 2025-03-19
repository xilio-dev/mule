package com.stackoak.stackoak.application.actors.alg;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleAnalyse {
    /**
     * 点赞数量
     */
    private Integer likeCount;
    /**
     * 阅读量
     */
    private Integer viewCount;
    /**
     * 收藏量
     */
    private Integer collectCount;
    /**
     * 评论量
     */
    private Integer commentCount;
}
