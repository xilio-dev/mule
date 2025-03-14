package com.stackoak.stackoak.common.data.article;

import com.stackoak.stackoak.common.data.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListByUserQuery extends PageQuery {
    private Integer status;
    private String key;
}
