package com.stackoka.stackoka.common.data.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotEmpty
    private String aid;
    @NotEmpty
    private String content;
    @NotEmpty
    private String commentPid;

}
