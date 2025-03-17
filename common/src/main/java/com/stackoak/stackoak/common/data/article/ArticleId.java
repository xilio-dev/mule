package com.stackoak.stackoak.common.data.article;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

/**
 * @param aid 文章编号
 */
public record ArticleId(@NotEmpty String aid) implements Serializable {

}
