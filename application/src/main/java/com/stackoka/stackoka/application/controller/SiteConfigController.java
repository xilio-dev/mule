package com.stackoka.stackoka.application.controller;

import com.stackoka.stackoka.application.service.siteconfig.ISiteConfigService;
import com.stackoka.stackoka.common.data.siteconfig.SiteConfig;
import com.stackoka.stackoka.common.message.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 09:15:27
 */
@RestController
@RequestMapping("/site-config")
public class SiteConfigController {
    @Autowired
    private ISiteConfigService siteConfigService;
    @GetMapping("info")
     @Cacheable(value = "site_config_info")
    public RestResult config(){
        SiteConfig siteConfig = siteConfigService.getById("1");
        return RestResult.success(siteConfig);
    }

}
