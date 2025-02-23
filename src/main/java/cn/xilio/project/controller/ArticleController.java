package cn.xilio.project.controller;

import cn.xilio.project.bo.Article;
import cn.xilio.project.domain.ArticleListDTO;
import cn.xilio.project.domain.DelArticleDTO;
import cn.xilio.project.domain.vo.article.add.SaveArticleDTO;
import cn.xilio.project.domain.vo.article.detail.ArticleDetailVO;
import cn.xilio.project.domain.vo.article.get.GetArticleVO;
import cn.xilio.project.service.IArticleService;
import cn.xilio.project.common.Result;
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
 * @author xilio.cn
 * @since 2025-02-15
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
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

    @GetMapping("detail/{id}")
    @Cacheable(value = "articleDetails", key = "#id")
    public Result<ArticleDetailVO> detail(@PathVariable @Valid @NotNull Long id) {
        return Result.success(articleService.detail(id));
    }

    // 新增add方法，用于添加文章
    @PostMapping("add")
    public Result add(@RequestBody SaveArticleDTO article) {
        articleService.addArticle(article);
        return Result.success();
    }

    @DeleteMapping("del")
    @Caching(evict = {
            @CacheEvict(value = "articleDetails", key = "#delArticleDTO.id"),
            @CacheEvict(value = "articleList", allEntries = true)
    })
    public Object delete(@RequestBody DelArticleDTO delArticleDTO) {
        return articleService.removeById(delArticleDTO.getId());
    }

    @PostMapping("/update")
    @CacheEvict(value = "articleDetails", key = "#article.id")
    public Result update(@RequestBody SaveArticleDTO article) {
        articleService.updateArticle(article);
        return Result.success();
    }

    @GetMapping("get/{id}")
    public Result<GetArticleVO> get(@PathVariable @Valid @NotNull Long id) {
        return Result.success(articleService.getArticleById(id));
    }
    //清理整个articleDetails缓存
//    @CacheEvict(value = "articleDetails", allEntries = true)
//    public Object update(@RequestBody Article article) {
//        return articleService.updateById(article);
//    }
}
