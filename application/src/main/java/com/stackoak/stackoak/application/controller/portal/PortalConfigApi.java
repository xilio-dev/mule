package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.service.siteconfig.ISiteConfigService;
import com.stackoak.stackoak.common.data.siteconfig.SiteConfig;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 09:15:27
 */
@Tag(name = "配置")
@RestController
@RequestMapping("/config")
public class PortalConfigApi {
    @Autowired
    private ISiteConfigService siteConfigService;


    @GetMapping("get-sys-config")
    @FieldFilter(type = SiteConfig.class, exclude = "id")
    public Result getSysConfig() {
        SiteConfig siteConfig = siteConfigService.getById("1");
        return Result.success(siteConfig);
    }
}
