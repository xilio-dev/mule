package com.stackoak.stackoak.common.data.article;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * ArticleListDTO 用于表示文章列表查询的参数。
 *
 * @param categoryId 分类 ID，必须为非负数
 * @param showType   显示类型，必须为正数
 * @param current    当前页码，必须为非负数
 * @param size       每页大小，必须为非负数
 */
public record ArticleListDTO(
        @PositiveOrZero
        @NotNull
        Long categoryId,

        @Positive
        @NotNull
        Integer showType,

        @PositiveOrZero
        @NotNull
        Long current,

        @PositiveOrZero
        @NotNull
        Long size
) {
}
