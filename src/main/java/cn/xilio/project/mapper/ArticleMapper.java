package cn.xilio.project.mapper;

import cn.xilio.project.bo.Article;
import cn.xilio.project.domain.ArticleListDTO;
import cn.xilio.project.domain.vo.ArticleBriefVO;
import cn.xilio.project.domain.vo.article.detail.ArticleDetailVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-15
 */
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<ArticleBriefVO> selectByCategoryAndRecent(@Param("page") Page<ArticleBriefVO> page, @Param("dto") ArticleListDTO articleListDTO);

    ArticleDetailVO selectArticleDetail(@Param("id") String id);
}

