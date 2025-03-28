package com.stackoak.stackoak.common.data.search;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Data
public class SearchRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 搜索类型
     */
    private Integer type;
    /**
     * 分页
     */
    @Range(min = 1, max = 100, message = "页数范围是[1,100]")
    private int page;
    /**
     * 页数限制
     */
    @Range(min = 1, max = 1000, message = "记录数限制在1-1000")
    private int size;
}
