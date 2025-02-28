package com.stackoka.stackoka.application.service.comment;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoka.stackoka.common.data.comment.CommentDTO;
import com.stackoka.stackoka.common.data.comment.CommentId;
import com.stackoka.stackoka.common.data.comment.Comments;
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
public interface ICommentsService extends IService<Comments> {
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

    List<CommentDTO> getCommentByAid(@NotEmpty String aid);
}
