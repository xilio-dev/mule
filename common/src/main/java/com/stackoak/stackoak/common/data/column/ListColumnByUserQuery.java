package com.stackoak.stackoak.common.data.column;

import com.stackoak.stackoak.common.data.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListColumnByUserQuery extends PageQuery {
    private Integer status;
    private String key;
}
