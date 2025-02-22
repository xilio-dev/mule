package cn.xilio.project.controller;

import cn.xilio.project.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-15
 */
@RestController
@RequestMapping("/user")
public class MemberController {
    @GetMapping("get")
    public Result get() {
        Map<Object, Object> userInfo = new HashMap<>();
        userInfo.put("username", "StackOak");
        userInfo.put("avatar", "https://unifolm-cdn.unitree.com/forum/static/2025/2/6/8cfe6fead4254768b7d1168a7d9f2bc3_800x800.jpeg");
        return Result.success(userInfo);
    }
}
