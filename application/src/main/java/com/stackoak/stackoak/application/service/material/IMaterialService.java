package com.stackoak.stackoak.application.service.material;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.material.Material;
import com.stackoak.stackoak.common.data.material.UploadResultDTO;
import org.springframework.web.multipart.MultipartFile;

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


    Page<Material> getMaterialListByUser(String userId, CommonPageQuery pageQuery);


    UploadResultDTO uploadImage(MultipartFile file);


    void bindAsMaterial(String materialId, String userId);
}
