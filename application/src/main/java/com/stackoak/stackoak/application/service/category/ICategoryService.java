package com.stackoak.stackoak.application.service.category;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.category.Category;
import com.stackoak.stackoak.common.data.category.CategoryTreeDTO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
public interface ICategoryService extends IService<Category> {
    /**
     * 二级分类树
     * @return
     */
    public List<CategoryTreeDTO> twoLevelTreenList();
}
