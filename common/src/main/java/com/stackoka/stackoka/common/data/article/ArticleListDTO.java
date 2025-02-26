package com.stackoka.stackoka.common.data.article;

import com.stackoka.stackoka.common.data.BaseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ArticleListDTO extends BaseDTO {
    @PositiveOrZero
    @NotNull
    private Long categoryId;
    @Positive
    @NotNull
    private Integer showType;
    @PositiveOrZero
    @NotNull
    private Long current;
    @PositiveOrZero
    @NotNull
    private Long size;
}
