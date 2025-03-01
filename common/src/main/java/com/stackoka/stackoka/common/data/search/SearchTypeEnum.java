package com.stackoka.stackoka.common.data.search;

import lombok.Getter;

@Getter
public enum SearchTypeEnum {
    BLOG(1, "博客搜索"),
    ;

    private final Integer type;
    private final String desc;

    SearchTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
