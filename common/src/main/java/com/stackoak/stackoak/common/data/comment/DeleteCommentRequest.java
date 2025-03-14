package com.stackoak.stackoak.common.data.comment;

import lombok.Data;

import java.io.Serializable;
@Data
public class DeleteCommentRequest implements Serializable {
    private String aid;
    private String commentId;
}
