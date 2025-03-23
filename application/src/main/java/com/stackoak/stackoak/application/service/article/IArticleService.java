package com.stackoak.stackoak.application.service.article;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.*;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
public interface IArticleService extends IService<Article> {

    public Page<ArticleBriefVO> listByCategory(ArticleListDTO articleListDTO);

    ArticleDetailVO detail(ArticleDetailDTO dto);

    String saveArticle(SaveArticleDTO article);

    void updateArticle(SaveArticleDTO article);

    GetArticleVO getArticleById(@Valid @NotNull String id);

    Page<ArticleBriefVO> selectByCategoryAndRecent(Page<ArticleBriefVO> page, ArticleListDTO articleListDTO);

    /**
     * 文章点赞
     *
     * @param articleId 文章ID
     */
    void digg(ArticleId articleId);

    /**
     * 取消文章点赞
     *
     * @param articleId 文章ID
     */
    void cancelDigg(ArticleId articleId);

    /**
     * 将文章添加到收藏夹
     *
     * @param favorRequest 收藏请求
     */
    void addToFavor(@Valid FavorRequest favorRequest);

    /**
     * 从收藏夹中取消文章收藏
     *
     * @param favorRequest 取消收藏请求信息
     */
    void fromFavorDel(@Valid FavorRequest favorRequest);

    /**
     * 获取用户公开发表的文章
     * @param request 请求参数
     * @return 文章列表
     */
    Page<ArticleBriefVO> getPublishArticle(@Valid ArticleQueryRequest request);

    void deleteArticle(ArticleId aid);

    Page<Article> authorRankList(@Valid AuthorRankListQuery request);

    Page<ArticleBriefVO> userRecentArticle(@Valid UserRecentArticleQuery request);

    Page<Article> listByUser(@Valid ListByUserQuery request);

    Page<Article> listByUserAndCategory(@Valid ListByUserAndCategoryQuery request);

    Page<Article> listByUserAndColumn(ListByUserAndCategoryQuery query);

    public Page<ArticleBriefVO>  followList( PageQuery pageQuery);

    Page<ArticleBriefVO> authorArticleList(@Valid CommonPageQuery dto);


    public Page<ArticleBriefVO>  getArticleListByColumn(@Valid CommonPageQuery query);

    public Page<ArticleBriefVO>  findAuthorHotArticleList(@Valid CommonPageQuery query);

    public Page<ArticleBriefVO> articleComprehensiveRank(PageQuery pageQuery);

}
