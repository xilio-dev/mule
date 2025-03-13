package com.stackoak.stackoak.common.data.column;

import com.stackoak.stackoak.common.data.PageQuery;
import lombok.Data;

@Data
public class ColumnQuery extends PageQuery {
    private String userId;
}
