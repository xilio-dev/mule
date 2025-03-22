package com.stackoak.stackoak.common.data.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stackoak.stackoak.common.data.article.UserInteractDTO;
import com.stackoak.stackoak.common.data.user.UserDTO;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO implements Serializable {
    /**
     * 评论ID，主键
     */
    private String id;
    /**
     * 评论父ID
     */
    private String pid;
    /**
     * 文章ID，逻辑关联posts表
     */
    private String articleId;

    /**
     * 评论者ID，
     */
    private String userId;
    /**
     * 评论者昵称
     */
    private String nickname;

    /**
     * 评论者头像
     */
    private String avatar;
    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt;
    /**
     * 评论回复  子评论
     */
    private List<CommentDTO> replies;
    /**
     * 被点赞的数量
     */
    private Integer likeCount;
    /**
     * 当前用户是否点赞（0：未点赞，1：已点赞）
     */
    private Integer liked;
    /**
     * 评论用户
     */
    private UserDTO user;
    /**
     * 被回复的用户
     */
    private UserDTO toUser;
    /**
     * 当前评论是否是作者发的
     */
    private Boolean isAuthor;

}
