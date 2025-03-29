package com.stackoak.stackoak.common.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommonPageQuery extends PageQuery {
    //通用 基于ID查询场景
    private String id;
    //通用 基于关键字查询场景
    private String keyword;
    //通用 基于类型分类查询场景
    private Integer type;


}
