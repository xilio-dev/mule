package com.stackoka.stackoka.application.controller;

import com.stackoka.stackoka.application.service.comment.ICommentsService;
import com.stackoka.stackoka.common.data.article.ArticleId;
import com.stackoka.stackoka.common.data.comment.CommentDiggRequest;
import com.stackoka.stackoka.common.message.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class CommentsController {
@Autowired
private ICommentsService commentsService;


    @PostMapping(value = "digg", name = "评论点赞")
    public RestResult digg(CommentDiggRequest commentDiggRequest) {
        commentsService.digg(commentDiggRequest);
        return RestResult.success();
    }

    @PutMapping(value = "undigg", name = "取消评论点赞")
    public RestResult unDigg(CommentDiggRequest commentDiggRequest) {
        commentsService.cancelDigg(commentDiggRequest);
        return RestResult.success();
    }

}
