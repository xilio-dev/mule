package com.stackoak.stackoak.application.service.recommend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;
import com.stackoak.stackoak.common.data.user.User;

public interface IRecommendService {
    /**
     * 基于用户进行个性化推荐
     * @param query 查询参数
     * @return 推荐列表
     */
    Page<ArticleBriefVO> getHomeArticleRecommend(PageQuery query);

    /**
     * 首页作者推荐
     * @param query 查询参数
     * @return 推荐作者列表
     */
    Page<User> homeAuthorRecommendByUserId(RecommendByUserQuery query);

    /**
     * 获取相关文章
     * @param query 查询参数
     * @return 推荐列表
     */
    Page<ArticleBriefVO> getRelatedArticles(RecommendByUserQuery query);
}
