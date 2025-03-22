package com.stackoak.stackoak.application.service.comment;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.comment.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * <p>
 * 评论表，存储用户对文章的评论 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
public interface ICommentsService extends IService<Comment> {
    /**
     * 评论点赞
     *
     * @param commentId 文章评论点赞请求
     */
    void digg(CommentId commentId);

    /**
     * 取消文章评论点赞
     *
     * @param commentId 取消评论点赞请求
     */
    void cancelDigg(CommentId commentId);

    Page<CommentDTO> getCommentByAid(@NotEmpty String aid);

    /**
     * 添加评论
     *
     * @param commentRequest 评论请求
     */
    void addComment(CommentRequest commentRequest);

    /**
     * 删除评论
     *
     * @param request 评论ID
     */
    void delCommentByUser(DeleteCommentRequest request);

    void delete(DeleteCommentRequest request);

    Page<CommentDTO>  getAllOneLevelComment(PageQuery pageQuery);

    Page<CommentDTO>  getAllOneLevelReply(PageQuery pageQuery);
}
