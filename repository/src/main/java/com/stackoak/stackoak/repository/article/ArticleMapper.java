package com.stackoak.stackoak.repository.article;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleDetailVO;
import com.stackoak.stackoak.common.data.article.ArticleListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;


/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
public interface ArticleMapper extends BaseMapper<Article> {

    Page<ArticleBriefVO> selectByCategoryAndRecent(@Param("page") Page<ArticleBriefVO> page, @Param("dto") ArticleListDTO articleListDTO);

    ArticleDetailVO selectArticleDetail(@Param("id") String id);

    @SelectProvider(type = ArticleSQLProvider.class, method = "selectUserRecentArticleList")
    ArticleDetailVO selectUserRecentArticleList(@Param("page") Page<ArticleBriefVO> page);

    Page<Article> selectByCategory(@Param("page") Page<Article> page, @Param("cid") String cid);

    Page<Article> selectByColumn(@Param("page") Page<Article> page, @Param("cid") String cid);
}

