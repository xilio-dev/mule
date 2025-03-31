package com.stackoak.stackoak.common.data.article;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ArticleData implements Serializable {
    private Integer likeCount;
    private Integer commentCount;
    private Integer collectCount;
    private Integer viewCount;
    private LocalDate date;
}
