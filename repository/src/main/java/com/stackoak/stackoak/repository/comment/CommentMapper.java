package com.stackoak.stackoak.repository.comment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.comment.Comment;
import com.stackoak.stackoak.common.data.comment.CommentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 评论表，存储用户对文章的评论 Mapper 接口
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentDTO> selectCommentByAid(@Param("page") Page<CommentDTO>page, @Param("aid") String aid, @Param("userId") String userId);
}

