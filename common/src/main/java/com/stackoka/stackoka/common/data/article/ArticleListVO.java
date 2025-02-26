package com.stackoka.stackoka.common.data.article;

import lombok.Data;

import java.io.Serializable;
@Data
public class ArticleListVO implements Serializable {
    private static final long serialVersionUID = 1L;
    //article
    private Long id;
    private String title;
}
