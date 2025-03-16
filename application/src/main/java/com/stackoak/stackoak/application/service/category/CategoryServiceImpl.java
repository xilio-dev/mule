package com.stackoak.stackoak.application.service.category;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.category.Category;
import com.stackoak.stackoak.common.data.category.CategoryTreeDTO;
import com.stackoak.stackoak.repository.category.CategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    /**
     * 二级分类树
     *
     * @return
     */
    @Override
    public List<CategoryTreeDTO> twoLevelTreenList() {
        List<Category> categories = list();

        return buildTwoLevelTree(categories);
    }
    private List<CategoryTreeDTO> buildTwoLevelTree(List<Category> categories) {
        // 1. 将分类数据按 pid 分组
        Map<String, List<CategoryTreeDTO>> pidMap = new HashMap<>();
        for (Category category : categories) {
            CategoryTreeDTO dto = new CategoryTreeDTO();
            BeanUtils.copyProperties(category, dto); // 将 Category 属性拷贝到 CategoryTreeDTO
            pidMap.computeIfAbsent(category.getPid(), k -> new ArrayList<>()).add(dto);
        }

        // 2. 构建二级分类树
        List<CategoryTreeDTO> result = new ArrayList<>();
        for (CategoryTreeDTO dto : pidMap.get(null)) { // 获取所有一级分类（pid 为 null）
            dto.setChildren(pidMap.get(dto.getId())); // 设置子分类
            result.add(dto);
        }

        return result;
    }
}
