package com.stackoka.stackoka.repository.article;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoka.stackoka.common.data.article.ArticleBriefVO;
import com.stackoka.stackoka.common.data.article.ArticleDO;
import com.stackoka.stackoka.common.data.article.ArticleDetailVO;
import com.stackoka.stackoka.common.data.article.ArticleListDTO;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
public interface ArticleMapper extends BaseMapper<ArticleDO> {

    IPage<ArticleBriefVO> selectByCategoryAndRecent(@Param("page") Page<ArticleBriefVO> page, @Param("dto") ArticleListDTO articleListDTO);

    ArticleDetailVO selectArticleDetail(@Param("id") String id);
}

