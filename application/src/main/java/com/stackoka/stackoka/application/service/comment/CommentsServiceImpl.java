package com.stackoka.stackoka.application.service.comment;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoka.stackoka.application.exception.BizException;
import com.stackoka.stackoka.application.service.like.ILikesService;
import com.stackoka.stackoka.common.data.article.ArticleDO;
import com.stackoka.stackoka.common.data.article.ArticleId;
import com.stackoka.stackoka.common.data.comment.CommentDiggRequest;
import com.stackoka.stackoka.common.data.comment.Comments;
import com.stackoka.stackoka.common.data.likes.LikeTypeEnum;
import com.stackoka.stackoka.common.data.likes.Likes;
import com.stackoka.stackoka.repository.comment.CommentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 评论表，存储用户对文章的评论 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

    @Autowired
    private ILikesService likesService;

    @Override
    public void digg(CommentDiggRequest commentDiggRequest) {
        diggAndUndigg(commentDiggRequest, 1);
    }

    @Override
    public void cancelDigg(CommentDiggRequest commentDiggRequest) {
        diggAndUndigg(commentDiggRequest, 0);
    }

    /**
     * 点赞或取消点赞
     *
     * @param request 评论点赞/取消请求
     * @param op      操作类型：1是点赞、0是取消点赞
     */
    private void diggAndUndigg(CommentDiggRequest request, Integer op) {
        //检查评论是否存在
        Comments comment = getById(request.getCommentId());
        if (ObjectUtils.isEmpty(comment)) {
            throw new BizException("评论不存在！");
        }
        Likes likes = new Likes();
        likes.setTargetId(comment.getId());
        likes.setUserId("1");//todo 临时用户
        likes.setType(LikeTypeEnum.COMMENT.getType());
        if (op == 1) {
            likesService.save(likes);
        } else {
            likesService.removeById(likes);
        }
    }
}
