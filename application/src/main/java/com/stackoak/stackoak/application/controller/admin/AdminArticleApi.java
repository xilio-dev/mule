package com.stackoak.stackoak.application.controller.admin;

import com.stackoak.stackoak.application.service.article.IArticleService;
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
@RestController
@RequestMapping("/article")
public class AdminArticleApi  {
    @Autowired
    private IArticleService articleService;

}
