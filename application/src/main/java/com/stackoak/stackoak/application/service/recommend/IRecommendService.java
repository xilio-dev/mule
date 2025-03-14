package com.stackoak.stackoak.application.service.recommend;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;

public interface IRecommendService {
    /**
     * 基于用户进行个性化推荐
     * @param query 查询参数
     * @return 推荐列表
     */
    Page<ArticleBriefVO> homeRecommendByUserId(RecommendByUserQuery query);
}
