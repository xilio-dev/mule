package com.stackoka.stackoka.common.data.comment;

import lombok.Data;

import java.io.Serializable;
@Data
public class CommentDiggRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String aid;
    private String commentId;
}
