package com.stackoak.stackoak.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommonPageQuery extends PageQuery {
    private String id;
    private String keyword;


}
