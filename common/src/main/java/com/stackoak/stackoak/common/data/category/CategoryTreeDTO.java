package com.stackoak.stackoak.common.data.category;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryTreeDTO extends Category {
    /**
     * 子分类
     */
    private List<CategoryTreeDTO> children;
}
