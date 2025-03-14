package com.stackoak.stackoak.application.controller.admin;

import com.stackoak.stackoak.application.service.follow.IFollowsService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 14:53:49
 */
@Tag(name = "关注接口")
@RestController
@RequestMapping("/user")
public class AdminFollowsApi {
    @Autowired
    private IFollowsService followsService;


}
