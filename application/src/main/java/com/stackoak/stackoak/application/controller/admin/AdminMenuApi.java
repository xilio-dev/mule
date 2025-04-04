package com.stackoak.stackoak.application.controller.admin;

import com.stackoak.stackoak.common.message.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class AdminMenuApi {
    @GetMapping("get-routes")
    public Result getRoutes() {
        Map<String, Object> systemMeta = new HashMap<>();
        systemMeta.put("title", "系统管理");
        systemMeta.put("icon", "system");
        systemMeta.put("noCache", false);
        systemMeta.put("link", null);
        ArrayList<Object> l = new ArrayList<>();
        l.add(systemMeta);
        return Result.success(l);
    }
}
