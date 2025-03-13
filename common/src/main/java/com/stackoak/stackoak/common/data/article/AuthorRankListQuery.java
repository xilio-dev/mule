package com.stackoak.stackoak.common.data.article;

import com.stackoak.stackoak.common.data.PageQuery;
import lombok.Data;

@Data
public class AuthorRankListQuery extends PageQuery {
    private String userId;
}
