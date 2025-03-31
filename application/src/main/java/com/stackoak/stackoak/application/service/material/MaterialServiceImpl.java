package com.stackoak.stackoak.application.service.material;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.application.service.common.IUploadService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.material.Material;
import com.stackoak.stackoak.common.data.material.MaterialStatus;
import com.stackoak.stackoak.common.data.material.MaterialType;
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
    public Page<Material> getMaterialListByUser(String userId, CommonPageQuery pageQuery) {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Material::getSpice, true);
        wrapper.eq(Material::getUserId, userId);
        return page(Page.of(pageQuery.getCurrent(), pageQuery.getSize()), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResultDTO uploadImage(MultipartFile file) {
        UploadResultDTO uploadResult = uploadService.uploadImage(file);
        Material material = new Material();
        material.setImgUrl(uploadResult.imgUrl());
        material.setSpice(false);
        material.setTitle(uploadResult.originName());
        material.setType(MaterialType.USER.getCode());
        //todo material.setStatus(MaterialStatus.AUDIT.getCode());/*审核中*/
        this.save(material);
        return uploadResult;
    }

    @Override
    public void bindAsMaterial(String materialId, String userId) {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Material::getId, materialId);
        wrapper.eq(Material::getUserId, userId);
        wrapper.eq(Material::getType, MaterialType.USER.getCode());
        getOneOpt(wrapper).ifPresentOrElse(material -> {
            material.setSpice(true);/*作为素材*/
            material.setUserId(userId);
            updateById(material);
        }, () -> {
            throw new BizException("素材不存在!");
        });
    }

    @Override
    public Page<Material> getSystemMaterialList(CommonPageQuery pageQuery) {
        LambdaQueryWrapper<Material> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Material::getType, MaterialType.SYSTEM.getCode());
        wrapper.eq(Material::getStatus, MaterialStatus.NORMAL.getCode());
        return page(Page.of(pageQuery.getCurrent(), pageQuery.getSize()), wrapper);
    }
}
