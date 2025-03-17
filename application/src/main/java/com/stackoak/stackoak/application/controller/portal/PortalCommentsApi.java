package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.actors.repeat.RepeatSubmit;
import com.stackoak.stackoak.application.service.comment.ICommentsService;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleId;
import com.stackoak.stackoak.common.data.comment.CommentId;
import com.stackoak.stackoak.common.data.comment.CommentRequest;
import com.stackoak.stackoak.common.data.comment.DeleteCommentRequest;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "评论")
@RestController
@RequestMapping("/comments")
public class PortalCommentsApi {
    @Autowired
    private ICommentsService commentsService;

    @PostMapping("list")
    public Result list(@RequestBody ArticleId articleId) {
        return Result.success(commentsService.getCommentByAid(articleId.aid()));
    }

    @PostMapping(value = "digg", name = "评论点赞")
    public Result digg(@RequestBody @Valid CommentId commentId) {
        commentsService.digg(commentId);
        return Result.success();
    }

    @PutMapping(value = "undigg", name = "取消评论点赞")
    public Result unDigg(CommentId commentId) {
        commentsService.cancelDigg(commentId);
        return Result.success();
    }

    @PostMapping(value = "add", name = "添加评论")
    @RepeatSubmit(expireTime = 3)
    public Result add(@RequestBody @Valid CommentRequest commentRequest) {
        commentsService.addComment(commentRequest);
        return Result.success();
    }

    @PostMapping(value = "del", name = "评论者删除评论")
    public Result delCommentByUser(@RequestBody DeleteCommentRequest request) {
        commentsService.delCommentByUser(request);
        return Result.success();
    }
    @PostMapping(value = "delete", name = "删除评论")
    public Result delete(@RequestBody DeleteCommentRequest request) {
        commentsService.delete(request);
        return Result.success();
    }


}
