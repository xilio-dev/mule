package cn.xilio.project.controller;

import cn.xilio.project.bo.Article;
import cn.xilio.project.bo.Category;
import cn.xilio.project.domain.ArticleListDTO;
import cn.xilio.project.domain.DelArticleDTO;
import cn.xilio.project.service.IArticleService;
import cn.xilio.project.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    @Cacheable(value = "articleList")
    public Object list(@RequestBody ArticleListDTO articleListDTO) {
        return articleService.list();
    }

    @PostMapping("search")
    @Cacheable(value = "searchArticleList")
    public Object search(@RequestParam String keyword) {
        return articleService.list();
    }

    @GetMapping("detail/{id}")
    @Cacheable(value = "articleDetails", key = "#id")
    public Object detail(@PathVariable Long id) {
        return articleService.getById(id);
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
    public Object update(@RequestBody Article article) {
        return articleService.updateById(article);
    }
    //清理整个articleDetails缓存
//    @CacheEvict(value = "articleDetails", allEntries = true)
//    public Object update(@RequestBody Article article) {
//        return articleService.updateById(article);
//    }
}
