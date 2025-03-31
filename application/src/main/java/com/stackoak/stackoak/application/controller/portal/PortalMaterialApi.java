package com.stackoak.stackoak.application.controller.portal;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.material.IMaterialService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.material.MaterialId;
import com.stackoak.stackoak.common.data.material.UploadResultDTO;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-24 18:00:56
 */
@Tag(name = "素材")
@RestController
@RequestMapping("/material")
public class PortalMaterialApi {
    @Autowired
    private IMaterialService materialService;

    @GetMapping("list")
    public Result list(@RequestBody CommonPageQuery pageQuery){
        String userId = StpKit.USER.getLoginIdAsString();
        return Result.success(this.materialService.getMaterialListByUser(userId,pageQuery));
    }
    @PutMapping("bind")
    public Result bind(@RequestBody MaterialId materialId){
        String userId = StpKit.USER.getLoginIdAsString();
        materialService.bindAsMaterial(materialId.getId(),userId);
        return Result.success();
    }

    @PostMapping("image/upload")
    public Result uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
      UploadResultDTO dto= materialService.uploadImage(file);
        return Result.success(dto);
    }
}
