package com.stackoak.stackoak.application.service.material;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.material.ThemePhoto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-26 01:28:37
 */
public interface IThemePhotoService extends IService<ThemePhoto> {

    Page<ThemePhoto> listByType(CommonPageQuery request);
}
