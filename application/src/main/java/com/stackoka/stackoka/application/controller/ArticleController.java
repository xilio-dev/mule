package com.stackoka.stackoka.application.controller;

import com.stackoka.stackoka.common.data.article.*;
import com.stackoka.stackoka.application.service.article.IArticleService;
import com.stackoka.stackoka.common.message.RestResult;
import com.stackoka.stackoka.common.message.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    private IArticleService articleService;

    @PostMapping("list")
    // @Cacheable(value = "articleList"/*, key = "#dto.categoryId + '-' + #dto.showType"*/)
    public Result list(@RequestBody @Valid ArticleListDTO dto) {
        return Result.success(articleService.listByCategory(dto));
    }

    @PostMapping("search")
    @Cacheable(value = "searchArticleList")
    public Object search(@RequestParam String keyword) {
        return articleService.list();
    }

    @PostMapping("detail")
    // @Cacheable(value = "articleDetails", key = "#dto.id")
    public Result<ArticleDetailVO> detail(@RequestBody @Valid ArticleDetailDTO dto) {
        return Result.success(articleService.detail(dto));
    }

    // 新增add方法，用于添加文章
    @PostMapping("add")
    public Result add(@RequestBody SaveArticleDTO article) {
        return Result.success(articleService.saveArticle(article));
    }

    @DeleteMapping("del")
    @Caching(evict = {
            @CacheEvict(value = "articleDetails", key = "#delArticleDTO.id"),
            @CacheEvict(value = "articleList", allEntries = true)
    })
    public Object delete(@RequestBody DelArticleDTO delArticleDTO) {
        return articleService.removeById(delArticleDTO.getId());
    }

    @PutMapping("update")
    //@CacheEvict(value = "articleDetails", key = "#article.id")
    public RestResult update(@RequestBody SaveArticleDTO article) {
        return RestResult.success(articleService.saveArticle(article));
    }

    @GetMapping("get/{id}")
    public Result<GetArticleVO> get(@PathVariable @Valid @NotNull String id) {
        return Result.success(articleService.getArticleById(id));
    }
    //清理整个articleDetails缓存
//    @CacheEvict(value = "articleDetails", allEntries = true)
//    public Object update(@RequestBody Article article) {
//        return articleService.updateById(article);
//    }

    @PostMapping(value = "digg", name = "文章点赞")
    public RestResult digg(ArticleId articleId) {
        articleService.digg(articleId);
        return RestResult.success();
    }

    @PutMapping(value = "undigg", name = "取消文章点赞")
    public RestResult unDigg(ArticleId articleId) {
        articleService.cancelDigg(articleId);
        return RestResult.success();
    }

}
