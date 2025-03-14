package com.stackoak.stackoak.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommonPageQuery extends PageQuery {
    private String keyword;
}
