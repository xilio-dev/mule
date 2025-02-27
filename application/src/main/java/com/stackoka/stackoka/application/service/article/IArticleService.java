package com.stackoka.stackoka.application.service.article;


import com.stackoka.stackoka.common.data.article.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
public interface IArticleService extends IService<Article> {

    public IPage<ArticleBriefVO> listByCategory(ArticleListDTO articleListDTO);

    ArticleDetailVO detail(ArticleDetailDTO dto);

    String saveArticle(SaveArticleDTO article);

    void updateArticle(SaveArticleDTO article);

    GetArticleVO getArticleById(@Valid @NotNull String id);

    IPage<ArticleBriefVO> selectByCategoryAndRecent(Page<ArticleBriefVO> page, ArticleListDTO articleListDTO);

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
}
