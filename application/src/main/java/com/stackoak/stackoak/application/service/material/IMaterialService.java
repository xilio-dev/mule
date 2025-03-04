package com.stackoak.stackoak.application.service.material;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.material.Material;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-24 17:49:31
 */
public interface IMaterialService extends IService<Material> {

    List<Material> userMaterialList();


}
