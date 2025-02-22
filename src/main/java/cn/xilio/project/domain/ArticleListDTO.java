package cn.xilio.project.domain;

import lombok.Data;

@Data
public class ArticleListDTO extends BaseDTO{
    private Long categoryId;
    private Integer queryType;
    private Long current;
    private Long size;
}
