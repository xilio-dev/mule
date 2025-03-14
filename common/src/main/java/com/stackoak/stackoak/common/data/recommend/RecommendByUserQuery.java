package com.stackoak.stackoak.common.data.recommend;

import com.stackoak.stackoak.common.data.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RecommendByUserQuery extends PageQuery {
    private String userId;
}
