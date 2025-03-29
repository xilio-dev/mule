package com.stackoak.stackoak.application.controller.portal;


import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.actors.security.SaUserCheckLogin;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.user.IUserService;

import com.stackoak.stackoak.common.data.user.UpdateCoverRequest;
import com.stackoak.stackoak.common.data.user.UpdateProfileRequest;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@Tag(name = "会员用户")
@RestController
@RequestMapping("/user")
public class PortalUserApi {
    @Autowired
    private IUserService userService;


    @GetMapping("get")
    @FieldFilter(
            type = User.class,
            exclude = {"password" })
    public Result get() {
        return Result.success(userService.getCurrentUser());
    }

    @GetMapping("detail/{userId}")
    @FieldFilter(type = User.class,exclude = {"password"})
    public Result detail(@PathVariable("userId")@Valid @NotEmpty(message = "用户编号不能为空") String userId) {
        return Result.success(userService.getUserDetail(userId));
    }

    @PutMapping(value = "update-profile", name = "更新用户配置信息")
    public Result updateProfile(@RequestBody UpdateProfileRequest request) {
        userService.updateProfile(request);
        return Result.success();
    }

    @GetMapping(value = "get-profile", name = "获取用户配置信息")
    public Result getProfile() {
        UpdateProfileRequest info = userService.getProfile();
        return Result.success(info);
    }

    @PutMapping(value = "update_cover", name = "更新作者主页封面")
    public Result updateCover(@RequestBody @Valid UpdateCoverRequest request) {
        String userId = StpKit.USER.getLoginIdAsString();
        userService.updateCover(request,userId);
        return Result.success();
    }
}
