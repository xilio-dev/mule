package com.stackoka.stackoka.common.data.article;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleId implements Serializable {
    @NotEmpty
    private String aid;

}
