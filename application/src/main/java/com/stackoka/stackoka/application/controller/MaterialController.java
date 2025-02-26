package com.stackoka.stackoka.application.controller;


import com.stackoka.stackoka.common.data.Constants;
import com.stackoka.stackoka.common.data.material.Material;
import com.stackoka.stackoka.common.data.material.MaterialId;
import com.stackoka.stackoka.common.message.RestResult;
import com.stackoka.stackoka.application.config.StackOkaConfig;
import com.stackoka.stackoka.application.service.material.IMaterialService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-24 18:00:56
 */
@RestController
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    private IMaterialService materialService;

    @GetMapping("list")
    public RestResult list(){
        return RestResult.success(this.materialService.userMaterialList());
    }
    @PutMapping("bind")
    public RestResult bind(@RequestBody MaterialId materialId){
        Material material =  materialService.getById(materialId.getId());
        material.setSpice(1);
        material.setUserId("1");//todo 用户信息获取
        materialService.updateById(material);
        return RestResult.success();
    }

    @PostMapping("image/upload")
    @Transactional(rollbackFor = Exception.class)
    public RestResult uploadImage(@RequestParam("imgFile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            RestResult restResult = new RestResult();
            String absPath = getAbsoluteFile(StackOkaConfig.getUploadPath(), fileName).getAbsolutePath();
            file.transferTo(Paths.get(absPath));
            String img = getPathFileName(StackOkaConfig.getUploadPath(), fileName);

            Material material = new Material();
            material.setImgUrl(img);
            material.setSpice(0);
            material.setTitle(fileName);
            material.setType(2);
            materialService.save(material);
            restResult.put("imgUrl", img);
            restResult.put("id", material.getId());
            return restResult;

        }
        return RestResult.error();
    }

    public static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = StackOkaConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
    }

    public File getAbsoluteFile(String uploadDir, String fileName) {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }
}
