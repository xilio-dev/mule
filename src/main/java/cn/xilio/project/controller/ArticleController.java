package cn.xilio.project.controller;

import cn.xilio.project.bo.Category;
import cn.xilio.project.service.IArticleService;
import cn.xilio.project.service.ICategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("detail/{id}")
    public Object detail(@PathVariable Long id) {
        return articleService.getById(id);
    }
}
