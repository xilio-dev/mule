package com.stackoak.stackoak.common.data.article;

import com.stackoak.stackoak.common.data.tag.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleBriefVO extends Article {
    /**
     * 标签列表
     */
    private List<Tag> tags;
    /**
     * 标签ID列表
     */
    private String tagIds;
    /**
     * 标签名字列表
     */
    private String tagNames;
    /**
     * 用户昵称
     */
    private String nickname;
}
