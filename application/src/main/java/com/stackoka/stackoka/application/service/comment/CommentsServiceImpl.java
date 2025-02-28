package com.stackoka.stackoka.application.service.comment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoka.stackoka.application.exception.BizException;
import com.stackoka.stackoka.application.service.article.IArticleService;
import com.stackoka.stackoka.application.service.like.ILikesService;
import com.stackoka.stackoka.application.service.user.IUserService;
import com.stackoka.stackoka.common.data.article.Article;
import com.stackoka.stackoka.common.data.comment.CommentDTO;
import com.stackoka.stackoka.common.data.comment.CommentId;
import com.stackoka.stackoka.common.data.comment.CommentRequest;
import com.stackoka.stackoka.common.data.comment.Comment;
import com.stackoka.stackoka.common.data.likes.LikeTypeEnum;
import com.stackoka.stackoka.common.data.likes.Likes;
import com.stackoka.stackoka.common.data.user.User;
import com.stackoka.stackoka.common.data.user.UserDTO;
import com.stackoka.stackoka.repository.comment.CommentsMapper;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论表，存储用户对文章的评论 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comment> implements ICommentsService {

    @Autowired
    private ILikesService likesService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleService articleService;

    @Override
    public void digg(CommentId commentDiggRequest) {
        diggAndUndigg(commentDiggRequest, 1);
    }

    @Override
    public void cancelDigg(CommentId commentDiggRequest) {
        diggAndUndigg(commentDiggRequest, 0);
    }

    /**
     * 通过文章编号获取评论列表
     *
     * @param aid 文章ID
     * @return 二级评论
     */
    @Override
    public List<CommentDTO> getCommentByAid(String aid) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, aid);
        List<Comment> comments = list(wrapper);
        //转化为二级评论树 三级回复二级转化为 “三级以及以后评论用户” 回复 “二级评论用户”
        return tran2LevelTree(comments);
    }

    /**
     * 添加评论
     *
     * @param commentRequest 评论请求
     */
    @Override
    public void addComment(CommentRequest commentRequest) {
        String commentPid = commentRequest.getCommentPid();
        String aid = commentRequest.getAid();
        //检查文章是否存在
        Article article = articleService.getById(aid);
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("该文章不存在！");
        }
        Comment comments = new Comment();
        comments.setUserId("1");//todo 临时用户
        comments.setContent(commentRequest.getContent());
        //如果父评论是0直接添加，否则检查父评论是否存在
        if ("0".equalsIgnoreCase(commentPid)) {
            comments.setPid("0");
        }
        //查询依赖的父评论
        Comment pc = getById(commentPid);
        if (ObjectUtils.isEmpty(pc)) {
            throw new BizException("依赖的评论不存在！");
        }
        comments.setPid(pc.getPid());
        //保存评论
        save(comments);
    }

    /**
     * 删除评论
     *
     * @param commentId 评论ID
     */
    @Override
    public void delComment(CommentId commentId) {
        //检查评论是否属于用户自己的评论，避免删除别人的
        Comment comments = getCommentByUser(commentId.getCommentId());
        if (ObjectUtils.isEmpty(comments)) {
            throw new BizException("评论不存在！");
        }
        removeById(commentId);
    }

    /**
     * 根据评论编号获取用户的评论信息
     *
     * @param commentId 评论编号
     * @return 评论
     */

    private Comment getCommentByUser(@NotEmpty(message = "评论ID不能为空") String commentId) {
        String userId = "1";//todo 临时测试
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getUserId, userId);
        wrapper.eq(Comment::getId, commentId);
        return getOne(wrapper);
    }

    private List<CommentDTO> tran2LevelTree(List<Comment> comments) {
        Map<String, CommentDTO> commentMap = new HashMap<>();
        List<CommentDTO> result = new ArrayList<>();
        // 第一次遍历：将所有评论存入 Map
        for (Comment comment : comments) {
            CommentDTO dto = new CommentDTO();
            dto.setId(comment.getId());
            dto.setContent(comment.getContent());
            dto.setLikeCount(comment.getLikeCount());
            dto.setLiked(0); // 默认未点赞
            dto.setCreatedAt(comment.getCreatedAt()); // 格式化时间
            dto.setUser(getUserDTO(comment.getUserId())); // 设置评论用户
            dto.setToUser(null); // 默认没有回复对象
            dto.setReplies(new ArrayList<>()); // 初始化子评论列表
            commentMap.put(comment.getId(), dto);
        }

        // 第二次遍历：构建树形结构
        for (Comment comment : comments) {
            if ("0".equalsIgnoreCase(comment.getPid())) {
                // 顶级评论，直接添加到结果列表
                result.add(commentMap.get(comment.getId()));
            } else {
                // 二级评论，挂载到对应的顶级评论下
                CommentDTO parent = commentMap.get(comment.getPid());
                if (parent != null) {
                    // 设置被回复的用户
                    CommentDTO reply = commentMap.get(comment.getId());
                    reply.setToUser(parent.getUser());
                    parent.getReplies().add(reply);
                }
            }
        }
        return result;
    }

    // 根据用户 ID 获取用户 DTO
    private UserDTO getUserDTO(String userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setDescription(user.getDescription());
        return userDTO;
    }

    /**
     * 点赞或取消点赞
     *
     * @param request 评论点赞/取消请求
     * @param op      操作类型：1是点赞、0是取消点赞
     */
    private void diggAndUndigg(CommentId request, Integer op) {
        //检查评论是否存在
        Comment comment = getById(request.getCommentId());
        if (ObjectUtils.isEmpty(comment)) {
            throw new BizException("评论不存在！");
        }
        Likes likes = new Likes();
        likes.setTargetId(comment.getId());
        likes.setUserId("1");//todo 临时用户
        likes.setType(LikeTypeEnum.COMMENT.getType());
        if (op == 1) {
            //检查是否已经点过赞了 避免重复点赞
            Likes like = likesService.getLike("1", request.getCommentId(), LikeTypeEnum.COMMENT);
            if (!ObjectUtils.isEmpty(like)) {
                throw new BizException("已经点过赞了");
            }
            likesService.save(likes);
        } else {
            likesService.removeById(likes);
        }
    }
}
