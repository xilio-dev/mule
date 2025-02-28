package com.stackoka.stackoka.common.data.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stackoka.stackoka.common.data.user.UserDTO;
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
     * 文章ID，逻辑关联posts表
     */
    private String articleId;

    /**
     * 评论者ID，逻辑关联users表
     */
    private String userId;


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
     * 是否点赞（0：未点赞，1：已点赞）
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

}
