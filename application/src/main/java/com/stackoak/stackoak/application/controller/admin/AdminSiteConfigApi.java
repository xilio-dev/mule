package com.stackoak.stackoak.application.controller.admin;

import com.stackoak.stackoak.application.service.siteconfig.ISiteConfigService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/config")
public class AdminSiteConfigApi {
    @Autowired
    private ISiteConfigService siteConfigService;


}
