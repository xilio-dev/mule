package com.stackoak.stackoak.application.service.material;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoak.stackoak.application.service.common.IUploadService;
import com.stackoak.stackoak.common.data.material.Material;
import com.stackoak.stackoak.common.data.material.UploadResultDTO;
import com.stackoak.stackoak.common.message.Result;
import com.stackoak.stackoak.repository.material.MaterialMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
    @Autowired
    private IUploadService uploadService;

    @Override
    public List<Material> userMaterialList() {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Material::getSpice, 1);
        wrapper.eq(Material::getUserId, "1");
        return baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResultDTO uploadImage(MultipartFile file) {
        UploadResultDTO uploadResult = uploadService.uploadImage(file);
        Material material = new Material();
        material.setImgUrl(uploadResult.imgUrl());
        material.setSpice(0);
        material.setTitle(uploadResult.originName());
        material.setType(2);
        this.save(material);
        return uploadResult;
    }
}
