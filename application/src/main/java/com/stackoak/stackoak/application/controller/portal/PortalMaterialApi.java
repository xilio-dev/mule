package com.stackoak.stackoak.application.controller.portal;



import com.google.gson.JsonObject;
import com.stackoak.stackoak.application.config.StackOakConfig;
import com.stackoak.stackoak.application.service.material.IMaterialService;
import com.stackoak.stackoak.common.data.Constants;
import com.stackoak.stackoak.common.data.material.Material;
import com.stackoak.stackoak.common.data.material.MaterialId;
import com.stackoak.stackoak.common.message.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

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
public class PortalMaterialApi {
    @Autowired
    private IMaterialService materialService;

    @GetMapping("list")
    public Result list(){
        return Result.success(this.materialService.userMaterialList());
    }
    @PutMapping("bind")
    public Result bind(@RequestBody MaterialId materialId){
        Material material =  materialService.getById(materialId.getId());
        material.setSpice(1);
        material.setUserId("1");//todo 用户信息获取
        materialService.updateById(material);
        return Result.success();
    }

    @PostMapping("image/upload")
    @Transactional(rollbackFor = Exception.class)
    public Result uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Result result = new Result();
            String absPath = getAbsoluteFile(StackOakConfig.getUploadPath(), fileName).getAbsolutePath();
            file.transferTo(Paths.get(absPath));
            String img = getPathFileName(StackOakConfig.getUploadPath(), fileName);

            Material material = new Material();
            material.setImgUrl(img);
            material.setSpice(0);
            material.setTitle(fileName);
            material.setType(2);
            materialService.save(material);
            result.put("imgUrl", img);
            result.put("id", material.getId());
            HashMap data = new HashMap();
            data.put("imgUrl",img);
            data.put("id",material.getId());
            result.put("data",data);
            return Result.success(data);

        }
        return Result.error();
    }

    public static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = StackOakConfig.getProfile().length() + 1;
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
