package com.stackoak.stackoak.application.service.comment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.stackoak.stackoak.application.actors.mq.DelayedMessage;
import com.stackoak.stackoak.application.actors.mq.DelayedMessageService;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.application.service.like.ILikesService;
import com.stackoak.stackoak.application.service.notification.INotificationSettingService;
import com.stackoak.stackoak.application.service.notification.INotificationsService;
import com.stackoak.stackoak.application.service.user.IUserService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.comment.*;
import com.stackoak.stackoak.common.data.likes.LikeTypeEnum;
import com.stackoak.stackoak.common.data.likes.Like;
import com.stackoak.stackoak.common.data.notification.Notification;
import com.stackoak.stackoak.common.data.notification.NotificationSetting;
import com.stackoak.stackoak.common.data.notification.NotificationType;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.common.data.user.UserDTO;
import com.stackoak.stackoak.repository.comment.CommentMapper;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class CommentsServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentsService {

    @Autowired
    private ILikesService likesService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private INotificationSettingService notificationSettingService;
    @Autowired
    private INotificationsService notificationsService;
    @Autowired
    private DelayedMessageService delayedMessageService;

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
    public Page<CommentDTO> getCommentByAid(String aid) {
        String userId = StpKit.USER.getLoginIdAsString();
        Page<CommentDTO> page = Page.of(1, 100);
        Page<CommentDTO> comments = baseMapper.selectCommentByAid(page, aid, userId);

        //转化为二级评论树 三级回复二级转化为 “三级以及以后评论用户” 回复 “二级评论用户”
        List<CommentDTO> commentDTOS = tran2LevelTree(comments.getRecords());
        comments.setRecords(commentDTOS);
        return comments;
    }

    /**
     * 添加评论
     *
     * @param commentRequest 评论请求
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(CommentRequest commentRequest) {
        String userId = StpKit.USER.getLoginIdAsString();
        String commentPid = commentRequest.getCommentPid();
        String aid = commentRequest.getAid();
        //检查文章是否存在
        Article article = articleService.getById(aid);
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("该文章不存在！");
        }
        Comment comments = new Comment();
        comments.setUserId(userId);
        comments.setContent(commentRequest.getContent());
        comments.setArticleId(aid);
        comments.setStatus(1);/*审核状态todo 暂时设置为已审核状态*/
        //如果父评论是0直接添加，否则检查父评论是否存在
        if ("0".equalsIgnoreCase(commentPid)) {
            comments.setPid("0");
        } else {
            //查询依赖的父评论
            Comment pc = getById(commentPid);
            if (ObjectUtils.isEmpty(pc)) {
                throw new BizException("依赖的评论不存在！");
            }
            comments.setPid(commentPid);
        }
        //保存评论
        save(comments);
        //检查用户是否开启了评论通知功能，如果没有开启就不推送到消息队列了 todo 异步优化
        NotificationSetting notify = notificationSettingService.getById(article.getUserId());
        Notification notification = new Notification();
        notification.setType(NotificationType.COMMENT.getType());
        notification.setUserId(article.getUserId());
        HashMap<String, Object> content = new HashMap<>();
        content.put("reviewContent", comments.getContent());
        notification.setContent(content);
        //记录消息到数据库，可以考虑异步
        notificationsService.save(notification);
        //如果文章作者用户开启了消息通知才推送 添加到延时消息队列，定时消费
        if (notify.getAppEnabled() && notify.getCommentEnabled()) {
            DelayedMessage delayedMessage = new DelayedMessage();
            Map<String, Object> message = new HashMap<>(10);
            message.put("title", "评论消息");
            message.put("type", String.valueOf(NotificationType.COMMENT.getType()));
            message.put("userId", article.getUserId());
            message.put("content", new Gson().toJson(content));

            delayedMessage.setStreamKey("STACKOAK:MESSAGES:NOTIFICATION");
            delayedMessage.setContent(message);
            delayedMessage.setExecuteDateTime(20); // 1分钟后执行
            delayedMessageService.addMessage(delayedMessage);
        }
    }

    /**
     * 删除评论
     *
     * @param request 删除请求信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delCommentByUser(DeleteCommentRequest request) {
        //检查评论是否属于用户自己的评论，避免删除别人的
        Comment comments = getCommentByUser(request.getCommentId());
        if (ObjectUtils.isEmpty(comments)) {
            throw new BizException("评论不存在！");
        }
        //如果有子评论需要删除所有子评论
        removeByCommentPid(request.getCommentId());
        //删除当前评论
        removeById(request.getCommentId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteCommentRequest request) {
        Article article = articleService.getById(request.getAid());
        if (ObjectUtils.isEmpty(article)) {
            throw new BizException("文章不存在！");
        }
        String userId = article.getUserId();
        if (!userId.equals(StpKit.USER.getLoginIdAsString())) {
            throw new BizException("只有文章作者才能删除评论！");
        }
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getArticleId, request.getAid())
                .eq(Comment::getId, request.getCommentId());
        Comment comment = getOne(wrapper);
        if (ObjectUtils.isEmpty(comment)) {
            throw new BizException("评论不存在！");
        }
        //删除当前评论
        removeById(request.getCommentId());
        //如果有子评论需要删除所有子评论
        removeByCommentPid(request.getCommentId());

    }

    @Override
    public Page<CommentDTO> getAllOneLevelComment(PageQuery pageQuery) {
        Page<CommentDTO> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        String userId = StpKit.USER.getLoginIdAsString();
        return baseMapper.selectAllOneLevelComment(page, userId);
    }

    @Override
    public Page<CommentDTO> getAllOneLevelReply(PageQuery pageQuery) {
        return null;
    }

    /**
     * 删除子评论
     *
     * @param commentId。父评论编号
     */
    private void removeByCommentPid(String commentId) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPid, commentId);
        remove(wrapper);
    }

    /**
     * 根据评论编号获取用户的评论信息
     *
     * @param commentId 评论编号
     * @return 评论
     */

    private Comment getCommentByUser(@NotEmpty(message = "评论ID不能为空") String commentId) {
        String userId = StpKit.USER.getLoginIdAsString();
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getUserId, userId);
        wrapper.eq(Comment::getId, commentId);
        return getOne(wrapper);
    }

    private List<CommentDTO> tran2LevelTree(List<CommentDTO> comments) {
        Map<String, CommentDTO> commentMap = new HashMap<>();
        List<CommentDTO> result = new ArrayList<>();

        // 第一次遍历：将所有评论存入 Map，并记录 pid
        for (CommentDTO dto : comments) {
            dto.setUser(getUserDTO(dto.getUserId())); // 设置评论用户
            dto.setUserId(dto.getUserId());
            dto.setToUser(null); // 默认没有回复对象
            dto.setReplies(new ArrayList<>()); // 初始化子评论列表
            commentMap.put(dto.getId(), dto);
        }

        // 第二次遍历：构建树形结构
        for (CommentDTO comment : comments) {
            if ("0".equalsIgnoreCase(comment.getPid())) {
                // 一级评论，直接添加到结果列表
                result.add(commentMap.get(comment.getId()));
            } else {
                // 找到一级父评论
                CommentDTO topLevelParent = findTopLevelParent(commentMap, comments, comment.getPid());
                if (topLevelParent != null) {
                    // 设置被回复的用户
                    CommentDTO reply = commentMap.get(comment.getId());
                    reply.setToUser(commentMap.get(comment.getPid()).getUser());

                    // 将二级及以上的评论都挂在一级评论的 replies 下
                    topLevelParent.getReplies().add(reply);
                }
            }
        }
        return result;
    }

    // 查找一级父评论
    private CommentDTO findTopLevelParent(Map<String, CommentDTO> commentMap, List<CommentDTO> comments, String pid) {
        // 根据 pid 找到对应的 Comment
        CommentDTO parentComment = comments.stream()
                .filter(c -> c.getId().equals(pid))
                .findFirst()
                .orElse(null);

        if (parentComment == null) {
            return null;
        }
        // 如果父评论的 pid 是 "0"，说明它是一级评论
        if ("0".equalsIgnoreCase(parentComment.getPid())) {
            return commentMap.get(parentComment.getId());
        }

        // 否则继续向上查找
        return findTopLevelParent(commentMap, comments, parentComment.getPid());
    }

    // 根据用户 ID 获取用户 DTO
    private UserDTO getUserDTO(String userId) {
        User user = userService.getById(userId);
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setNickname(user.getNickname());
        userDTO.setAvatar(user.getAvatar());
        userDTO.setDescription(user.getIntroduce());
        return userDTO;
    }

    /**
     * 点赞或取消点赞
     *
     * @param request 评论点赞/取消请求
     * @param op      操作类型：1是点赞、0是取消点赞
     */
    private void diggAndUndigg(CommentId request, Integer op) {
        String userId = StpKit.USER.getLoginIdAsString();
        //检查评论是否存在
        Comment comment = getById(request.getCommentId());
        if (ObjectUtils.isEmpty(comment)) {
            throw new BizException("评论不存在！");
        }

        if (op == 1) {
            //检查是否已经点过赞了 避免重复点赞
            boolean isLiked = likesService.isLiked(userId, request.getCommentId(), LikeTypeEnum.COMMENT);
            if (isLiked) {
                throw new BizException("已经点过赞了");
            }
            Like newLike = new Like();
            newLike.setTargetId(comment.getId());
            newLike.setUserId(userId);
            newLike.setType(LikeTypeEnum.COMMENT.getType());
            likesService.save(newLike);
            //todo 评论的点赞数量+1
        } else {
            LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Like::getTargetId, comment.getId())
                    .eq(Like::getUserId, userId)
                    .eq(Like::getType, LikeTypeEnum.COMMENT.getType());
            likesService.remove(wrapper);
            //todo 评论的点赞数量-1
        }
    }
}
