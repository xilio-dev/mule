package com.stackoak.stackoak.common.data.likes;

import lombok.Getter;

@Getter
public enum LikeTypeEnum {
    ARTICLE(1, "点赞文章"),
    COMMENT(2, "点赞评论");

    private Integer type;
    private String desc;

    LikeTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
