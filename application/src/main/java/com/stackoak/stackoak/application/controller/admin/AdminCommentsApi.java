package com.stackoak.stackoak.application.controller.admin;

import com.stackoak.stackoak.application.service.comment.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论表，存储用户对文章的评论 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
@RestController
@RequestMapping("/comments")
public class AdminCommentsApi {
    @Autowired
    private ICommentsService commentsService;

}
