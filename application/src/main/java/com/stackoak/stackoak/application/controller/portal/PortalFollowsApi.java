package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stackoak.stackoak.application.service.follow.IFollowsService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.follow.FollowPageQuery;
import com.stackoak.stackoak.common.data.follow.FollowRequest;
import com.stackoak.stackoak.common.data.follow.FollowUserVO;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.Operation;
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
public class PortalFollowsApi {
    @Autowired
    private IFollowsService followsService;

    @Operation(summary = "关注作者")
    @PostMapping(value = "follow", name = "关注作者")
    public Result follow(@RequestParam("userId")String userId) {
        followsService.follow(userId);
        return Result.success();
    }

    @PutMapping(value = "unfollow", name = "取消关注")
    @Operation(summary = "取消关注")
    public Result unfollow(@RequestParam String userId) {
        followsService.cancelFollow(userId);
        return Result.success();
    }

    @PostMapping(value = "follows", name = "我关注的人")
    public Result follows(@RequestBody FollowPageQuery pageQuery) {
        IPage<FollowUserVO> follows = followsService.findFollow(pageQuery);
        return Result.success(follows);
    }

    @PostMapping(value = "fans", name = "关注我的人")
    public Result fans(@RequestBody FollowPageQuery pageQuery) {
        IPage<FollowUserVO> fans = followsService.findFans(pageQuery);
        return Result.success(fans);
    }

}
