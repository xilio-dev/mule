package com.stackoak.stackoak.common.data.article;


import com.stackoak.stackoak.common.data.column.Column;
import com.stackoak.stackoak.common.data.tag.Tag;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticleDetailVO implements Serializable {
    // 文章信息
    private ArticleInfoDTO articleInfo;
    // 用户信息
    private UserInfoDTO userInfo;
    // 标签信息
    private List<Tag> tags;
    // 用户互动信息
    private UserInteractDTO userInteract;
    // 分类信息
    private CategoryInfoDTO category;
    // 标签ID
    private String tagIds;
    // 标签名称
    private String tagNames;
    private List<Column> columns;
    private String columnIds;
    private String columnNames;
}
@Data
// 定义一个CategoryInfoDTO类，用于存储分类信息
class CategoryInfoDTO {
    // 分类ID
    private String categoryId;
    // 分类名称
    private String categoryName;
}

