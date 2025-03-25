package com.stackoak.stackoak.application.service.recommend;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.user.User;


import java.util.List;

public interface IRecommendService {
    /**
     * 个性化推荐用户文章
     *
     * @param userId    用户ID
     * @param pageQuery 分页参数
     * @return 推荐结果
     */
    Page<Article> getPersonalizedArticleRecommendations(String userId, PageQuery pageQuery);

    /**
     * 用户没有登陆时，提供默认推荐
     *
     * @param pageQuery 分页参数
     * @return 推荐结果
     */
    Page<Article> getDefaultArticleRecommendations(PageQuery pageQuery);

    Page<User> recommendAuthors(String userId, PageQuery pageQuery);

}
