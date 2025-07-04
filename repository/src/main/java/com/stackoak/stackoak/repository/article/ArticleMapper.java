package com.stackoak.stackoak.repository.article;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


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


    Page<ArticleBriefVO> selectFollowList(@Param("page") Page<ArticleBriefVO> page, @Param("userId") String userId);

    Page<ArticleBriefVO> selectAuthorArticleList(@Param("page") Page<ArticleBriefVO> page, @Param("userId") String userId, @Param("title") String title);

    Page<ArticleBriefVO> selectArticleListByColumnId(@Param("page") Page<ArticleBriefVO> page, @Param("cid") String cid);

    Page<ArticleBriefVO> selectAuthorHotArticleList(
            @Param("page") Page<ArticleBriefVO> page,
            @Param("userId") String userId,
            @Param("likeWeight") int likeWeight,
            @Param("viewWeight") int viewWeight,
            @Param("collectWeight") int collectWeight,
            @Param("commentWeight") int commentWeight,
            @Param("gravity") double gravity
    );

    Page<ArticleBriefVO> findComprehensiveRank(@Param("page") Page<ArticleBriefVO> page, @Param("likeWeight") int likeWeight,
                                               @Param("viewWeight") int viewWeight,
                                               @Param("collectWeight") int collectWeight,
                                               @Param("commentWeight") int commentWeight,
                                               @Param("gravity") double gravity);

    UserInteractDTO selectUserInteract(@Param("visitUserId") String visitUserId,@Param("authorId") String authorId,@Param("articleId") String articleId);

    /**
     * 根据文章id查询文章统计信息

     * @param articleId 文章ID
     * @param start 开始时间戳
     * @param end 结束时间戳
     * @return 文章统计信息列表
     */
    List<ArticleData> selectSingleArticleStatistics( @Param("articleId") String articleId, @Param("start") Long start, @Param("end") Long end);

}

