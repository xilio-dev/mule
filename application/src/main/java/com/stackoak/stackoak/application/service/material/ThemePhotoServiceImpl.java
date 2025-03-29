package com.stackoak.stackoak.application.service.material;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.material.ThemePhoto;
import com.stackoak.stackoak.common.data.material.ThemePhotoStatus;
import com.stackoak.stackoak.repository.material.ThemePhotoMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-26 01:28:37
 */
@Service
public class ThemePhotoServiceImpl extends ServiceImpl<ThemePhotoMapper, ThemePhoto> implements IThemePhotoService {

    @Override
    public Page<ThemePhoto> listByType(CommonPageQuery request) {
        Integer type = request.getType();
        BizException.noNull(type, "必须制定type！");
        LambdaQueryWrapper<ThemePhoto> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ThemePhoto::getType, type);
        wrapper.eq(ThemePhoto::getStatus, ThemePhotoStatus.NORMAL.getCode());
        return page(Page.of(request.getCurrent(), request.getSize()), wrapper);
    }
}
