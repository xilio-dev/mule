package com.stackoak.stackoak.application.controller.admin;


import com.stackoak.stackoak.application.service.material.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class AdminMaterialApi {
    @Autowired
    private IMaterialService materialService;


}
