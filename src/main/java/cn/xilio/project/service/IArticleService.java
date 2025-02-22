package cn.xilio.project.service;

import cn.xilio.project.bo.Article;
import cn.xilio.project.domain.ArticleListDTO;
import cn.xilio.project.domain.vo.ArticleBriefVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
