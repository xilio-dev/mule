package com.stackoka.stackoka.common.data.follow;

import lombok.Data;

import java.io.Serializable;

@Data
public class FollowUserVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;
    /**
     * 大头像链接
     */
    private String avatar;
    /**
     * 描述
     */
    private String description;
    /**
     * 表示当前登陆用户与作者的关系，1表示关注了作者，0表示无关注作者
     */
    private Integer relation;
}
