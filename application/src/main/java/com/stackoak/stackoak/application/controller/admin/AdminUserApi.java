package com.stackoak.stackoak.application.controller.admin;

import com.stackoak.stackoak.common.message.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@RestController
@RequestMapping("/user")
public class AdminUserApi {
    @PostMapping("login")
    public Result login(@RequestBody HashMap<String, Object> map) {
        map.put("role", List.of("admin"));
        map.put("token", "admin-token");
        return Result.success(map);
    }
}
