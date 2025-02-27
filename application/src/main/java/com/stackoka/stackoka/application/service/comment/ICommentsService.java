package com.stackoka.stackoka.application.service.comment;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoka.stackoka.common.data.comment.CommentDiggRequest;
import com.stackoka.stackoka.common.data.comment.Comments;

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
     * @param commentDiggRequest 文章评论点赞请求
     */
    void digg(CommentDiggRequest commentDiggRequest);

    /**
     * 取消文章评论点赞
     *
     * @param commentDiggRequest 取消评论点赞请求
     */
    void cancelDigg(CommentDiggRequest commentDiggRequest);

}
