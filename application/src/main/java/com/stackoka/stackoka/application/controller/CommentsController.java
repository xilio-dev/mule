package com.stackoka.stackoka.application.controller;

import com.stackoka.stackoka.application.service.comment.ICommentsService;
import com.stackoka.stackoka.common.data.article.ArticleId;
import com.stackoka.stackoka.common.data.comment.CommentId;
import com.stackoka.stackoka.common.data.comment.CommentRequest;
import com.stackoka.stackoka.common.message.RestResult;
import jakarta.validation.Valid;
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
public class CommentsController {
    @Autowired
    private ICommentsService commentsService;

    @PostMapping("list")
    public RestResult list(@RequestBody ArticleId articleId) {
        return RestResult.success(commentsService.getCommentByAid(articleId.getAid()));
    }

    @PostMapping(value = "digg", name = "评论点赞")
    public RestResult digg(@RequestBody @Valid CommentId commentId) {
        commentsService.digg(commentId);
        return RestResult.success();
    }

    @PutMapping(value = "undigg", name = "取消评论点赞")
    public RestResult unDigg(CommentId commentId) {
        commentsService.cancelDigg(commentId);
        return RestResult.success();
    }

    @PostMapping(value = "add", name = "添加评论")
    public RestResult add(@RequestBody @Valid CommentRequest commentRequest) {
        commentsService.addComment(commentRequest);
        return RestResult.success();
    }

    @PostMapping(value = "del", name = "删除评论")
    public RestResult delComment(@RequestBody CommentId commentId) {
        commentsService.delComment(commentId);
        return RestResult.success();
    }
}
