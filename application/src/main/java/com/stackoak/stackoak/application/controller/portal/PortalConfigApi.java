package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.actors.security.SaUserCheckLogin;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.siteconfig.ISiteConfigService;
import com.stackoak.stackoak.application.service.user.IUserConfigService;
import com.stackoak.stackoak.common.data.siteconfig.SiteConfig;
import com.stackoak.stackoak.common.data.user.UserConfig;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
@RestController
@RequestMapping("/config")
public class PortalConfigApi {
    @Autowired
    private ISiteConfigService siteConfigService;
    @Autowired
    private IUserConfigService userConfigService;

    @GetMapping("get-sys-config")
    @FieldFilter(type = SiteConfig.class, exclude = "id")
    public Result getSysConfig() {
        SiteConfig siteConfig = siteConfigService.getById("1");
        return Result.success(siteConfig);
    }

    @GetMapping("get-user-config")
    @SaUserCheckLogin
    @FieldFilter(type = UserConfig.class, exclude = "userId")
    public Result getUserConfig() {
        return Result.success(userConfigService.getById(StpKit.USER.getLoginIdAsString()));
    }
}
