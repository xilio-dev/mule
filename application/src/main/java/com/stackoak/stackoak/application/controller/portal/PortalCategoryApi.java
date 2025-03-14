package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.service.category.ICategoryService;
import com.stackoak.stackoak.common.data.category.Category;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@Tag(name = "分类领域")
@RestController
@RequestMapping("/category")
public class PortalCategoryApi {
    @Autowired
    private ICategoryService categoryService;
    @GetMapping("list")
    @FieldFilter( include = { "id","name","url","icon"},type = Category.class)
    //@Cacheable(value = "systemCategoryList", key = "'showType_1'", sync = false) // 缓存名称和键
    public Result systemCategoryList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getShowType, 1);
        wrapper.orderByAsc(Category::getSort);
        return  Result.success(categoryService.list(wrapper));
    }
}
