package com.stackoak.stackoak.application.service.recommend;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import com.stackoak.stackoak.common.data.user.User;


import java.util.List;

public interface IRecommendService {


    Page<ArticleBriefVO> getRecommendation(String userId, CommonPageQuery pageQuery);

    Page<ArticleBriefVO> getArticleSimilarityRecommender(String userId, CommonPageQuery pageQuery);


}
