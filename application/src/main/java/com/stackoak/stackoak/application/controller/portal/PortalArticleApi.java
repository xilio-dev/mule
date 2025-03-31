package com.stackoak.stackoak.application.controller.portal;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.alg.ContentHeatCalculator;
import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.actors.cut.FieldFilters;
import com.stackoak.stackoak.application.actors.limit.RateLimit;
import com.stackoak.stackoak.application.actors.recommand.BehaviorLog;
import com.stackoak.stackoak.application.actors.recommand.BehaviorType;
import com.stackoak.stackoak.application.actors.security.SaUserCheckLogin;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.*;
import com.stackoak.stackoak.application.service.article.IArticleService;

import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@Tag(name = "文章管理")
@RestController
@RequestMapping("/article")
public class PortalArticleApi {
    @Autowired
    private IArticleService articleService;


    @PostMapping("list")
    @FieldFilter(type = ArticleBriefVO.class)
    public Result list(@RequestBody @Valid ArticleListDTO dto) {
        return Result.success(articleService.listByCategory(dto));
    }

    @PostMapping("get_column_article_list")
    @FieldFilter(type = ArticleBriefVO.class)
    public Result getColumnArticleList(@RequestBody @Valid CommonPageQuery query) {
        return Result.success(articleService.getArticleListByColumn(query));
    }

    @PostMapping(value = "rank/comprehensive", name = "文章综合排行榜")
    @FieldFilter(type = ArticleBriefVO.class)
    public Result articleRank(@RequestBody PageQuery pageQuery) {
        return Result.success(articleService.articleComprehensiveRank(pageQuery));
    }

    @PostMapping(value = "get_author_hot_article_list", name = "获取作者热门文章")
    @FieldFilter(type = ArticleBriefVO.class)
    public Result getAuthorHotArticleList(@RequestBody @Valid CommonPageQuery query) {
        return Result.success(articleService.findAuthorHotArticleList(query));
    }

    @PostMapping(value = "follow_list", name = "关注作者的文章")
    @FieldFilter(type = ArticleBriefVO.class)
    @SaUserCheckLogin
    public Result followList(@RequestBody PageQuery pageQuery) {
        return Result.success(articleService.followList(pageQuery));
    }

    @PostMapping("author_article_list")
    @FieldFilter(type = ArticleBriefVO.class)
    public Result authorArticleList(@RequestBody @Valid CommonPageQuery dto) {
        return Result.success(articleService.authorArticleList(dto));
    }


    @PostMapping("search")
    public Object search(@RequestParam String keyword) {
        return articleService.list();
    }

    @PostMapping("detail")
    @RateLimit(limit = 1)
    @BehaviorLog(businessId = "#dto.id", type = BehaviorType.ViEW)
    @FieldFilters(value = {
            @FieldFilter(type = ArticleDetailVO.class, exclude = {"columnNames", "columnIds"}),
            @FieldFilter(type = ArticleInfoDTO.class, exclude = {"visitPassword"})}
    )
    public Result detail(@RequestBody @Valid ArticleDetailDTO dto) {
        return Result.success(articleService.detail(dto));
    }

    // 新增add方法，用于添加文章
    @PostMapping("add")
    public Result add(@RequestBody SaveArticleDTO article) {
        return Result.success(articleService.saveArticle(article));
    }

    @PostMapping("del")
    public Result delete(@RequestBody ArticleId articleId) {
        articleService.deleteArticle(articleId);
        return Result.success();
    }

    @PutMapping("update")
    //@CacheEvict(value = "articleDetails", key = "#article.id")
    public Result update(@RequestBody SaveArticleDTO article) {
        return Result.success(articleService.saveArticle(article));
    }

    @GetMapping("get/{id}")
    public Result get(@PathVariable @Valid @NotNull String id) {
        return Result.success(articleService.getArticleById(id));
    }

    @PostMapping(value = "digg/{aid}", name = "文章点赞")
    @BehaviorLog(businessId = "#aid", type = BehaviorType.LIKE)
    public Result digg(@PathVariable String aid) {
        articleService.digg(aid);
        return Result.success();
    }

    @PutMapping(value = "undigg/{aid}", name = "取消文章点赞")
    public Result unDigg(@PathVariable String aid) {
        articleService.cancelDigg(aid);
        return Result.success();
    }

    @PostMapping("addToFavor")
    public Result addToFavor(@RequestBody @Valid FavorRequest favorRequest) {
        articleService.addToFavor(favorRequest);
        return Result.success();
    }

    @DeleteMapping("fromFavorDel")
    public Result fromFavorDel(@RequestBody @Valid FavorRequest favorRequest) {
        articleService.fromFavorDel(favorRequest);
        return Result.success();
    }

    @PostMapping("query")
    public Result getPublishArticle(@RequestBody @Valid ArticleQueryRequest request) {
        Page<ArticleBriefVO> pages = articleService.getPublishArticle(request);
        return Result.success(pages);
    }

    @PostMapping(value = "recent", name = "作者主页最近文章")
    @FieldFilter(type = ArticleBriefVO.class, include = {"id", "title", "description", "viewCount", "likeCount", "commentCount", "publishTime"})
    public Result authorHomeRecentArticle(@RequestBody @Valid UserRecentArticleQuery request) {
        Page<ArticleBriefVO> pages = articleService.userRecentArticle(request);
        return Result.success(pages);
    }

    @PostMapping(value = "rank-list", name = "作者主页文章阅读排名")
    public Result authorRankList(@RequestBody @Valid AuthorRankListQuery request) {
        Page<Article> pages = articleService.authorRankList(request);
        return Result.success(pages);
    }

    @PostMapping(value = "list_by_user", name = "创作中心用户文章列表")
    public Result listByUser(@RequestBody @Valid ListByUserQuery request) {
        Page<Article> pages = articleService.listByUser(request);
        return Result.success(pages);
    }

    @PostMapping(value = "get-collect-article", name = "获取收藏夹中的文章")
    public Result getCollectArticle(@RequestBody ListByUserAndCategoryQuery query) {
        return Result.success(articleService.listByUserAndCategory(query));
    }

    @PostMapping(value = "get-column-article", name = "获取专栏中的文章")
    public Result getColumnArticle(@RequestBody ListByUserAndCategoryQuery query) {
        return Result.success(articleService.listByUserAndColumn(query));
    }

    @PostMapping(value = "single-article-statistics", name = "单篇文章数据分析")
    public Result singleArticleStatistics(@RequestBody PageQuery pageQuery, @RequestParam Long start, @RequestParam Long end  ) {
        return Result.success(articleService.singleArticleStatistics(pageQuery,start,end));
    }



}
