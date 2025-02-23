package cn.xilio.project.service;

import cn.xilio.project.bo.Article;
import cn.xilio.project.domain.ArticleListDTO;
import cn.xilio.project.domain.vo.ArticleBriefVO;
import cn.xilio.project.domain.vo.article.add.SaveArticleDTO;
import cn.xilio.project.domain.vo.article.detail.ArticleDetailVO;
import cn.xilio.project.domain.vo.article.get.GetArticleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-15
 */
public interface IArticleService extends IService<Article> {

   public IPage<ArticleBriefVO> listByCategory(ArticleListDTO articleListDTO);

   ArticleDetailVO detail(Long id);

    void addArticle(SaveArticleDTO article);

    void updateArticle(SaveArticleDTO article);

    GetArticleVO getArticleById(@Valid @NotNull Long id);
}
