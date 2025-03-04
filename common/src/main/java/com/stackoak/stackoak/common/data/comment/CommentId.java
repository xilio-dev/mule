package com.stackoak.stackoak.common.data.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentId implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotEmpty(message = "评论ID不能为空")
    private String commentId;
}
