package com.stackoak.stackoak.common.data.follow;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FollowRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 被关注用户的ID
     */
    @NotEmpty(message = "用户编号不能为空")
    private String userId;
}
