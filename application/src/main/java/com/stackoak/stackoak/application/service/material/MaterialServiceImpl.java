package com.stackoak.stackoak.application.service.material;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoak.stackoak.common.data.material.Material;
import com.stackoak.stackoak.repository.material.MaterialMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-24 17:49:31
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {
    @Override
    public List<Material> userMaterialList() {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Material::getSpice, 1);
        wrapper.eq(Material::getUserId, "1");
        return baseMapper.selectList(wrapper);
    }
}
