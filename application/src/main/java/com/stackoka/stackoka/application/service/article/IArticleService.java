package com.stackoka.stackoka.application.service.article;


import com.stackoka.stackoka.common.data.article.ArticleDetailDTO;
import com.stackoka.stackoka.common.data.article.ArticleListDTO;
import com.stackoka.stackoka.common.data.article.ArticleBriefVO;
import com.stackoka.stackoka.common.data.article.SaveArticleDTO;
import com.stackoka.stackoka.common.data.article.ArticleDetailVO;
import com.stackoka.stackoka.common.data.article.GetArticleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoka.stackoka.common.data.article.ArticleDO;
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
public interface IArticleService extends IService<ArticleDO> {

   public IPage<ArticleBriefVO> listByCategory(ArticleListDTO articleListDTO);

   ArticleDetailVO detail(ArticleDetailDTO dto);

    String addArticle(SaveArticleDTO article);

    void updateArticle(SaveArticleDTO article);

    GetArticleVO getArticleById(@Valid @NotNull String id);

    IPage<ArticleBriefVO> selectByCategoryAndRecent(Page<ArticleBriefVO> page, ArticleListDTO articleListDTO);
}
