package com.stackoka.stackoka.application.controller;

import com.stackoka.stackoka.application.actors.aop.auth.AuthCheck;

import com.stackoka.stackoka.common.data.article.UserRoleEnum;
import com.stackoka.stackoka.application.service.category.ICategoryService;
import com.stackoka.stackoka.common.data.category.Category;
import com.stackoka.stackoka.common.message.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;


    @GetMapping("list")
    @AuthCheck(roles = {UserRoleEnum.USER})
    @Cacheable(value = "systemCategoryList", key = "'showType_1'",sync = false) // 缓存名称和键
    public Result systemCategoryList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getShowType,1);
        wrapper.orderByAsc(Category::getSort);
        return Result.success(categoryService.list(wrapper));
    }
}
