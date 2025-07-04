package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.service.material.IThemePhotoService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.message.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-26 01:28:37
 */
@RestController
@RequestMapping("/theme_photo")
public class PortalThemePhotoApi {
    @Autowired
    private IThemePhotoService themePhotoService;

    @PostMapping("list")
    public Result list(@RequestBody @Valid CommonPageQuery request) {
        return Result.success(themePhotoService.listByType(request));
    }
}
