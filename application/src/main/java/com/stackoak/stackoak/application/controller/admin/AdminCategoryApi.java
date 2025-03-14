package com.stackoak.stackoak.application.controller.admin;


import com.stackoak.stackoak.application.service.category.ICategoryService;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/category")
public class AdminCategoryApi {
    @Autowired
    private ICategoryService categoryService;

}
