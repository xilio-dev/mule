package com.stackoka.stackoka.application.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stackoka.stackoka.application.service.follow.IFollowsService;
import com.stackoka.stackoka.common.data.PageQuery;
import com.stackoka.stackoka.common.data.follow.FollowRequest;
import com.stackoka.stackoka.common.data.follow.FollowUserVO;
import com.stackoka.stackoka.common.message.RestResult;

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
public class FollowsController {
    @Autowired
    private IFollowsService followsService;

    @Operation(summary = "关注作者")
    @PostMapping(value = "follow", name = "关注作者")
    public RestResult follow(@RequestBody FollowRequest followRequest) {
        followsService.follow(followRequest);
        return RestResult.success();
    }

    @DeleteMapping(value = "unfollow", name = "取消关注")
    public RestResult unfollow(@RequestBody FollowRequest followRequest) {
        followsService.cancelFollow(followRequest);
        return RestResult.success();
    }

    @PostMapping(value = "follows", name = "我关注的人")
    public RestResult follows(@RequestBody PageQuery pageQuery) {
        IPage<FollowUserVO> follows = followsService.findFollow(pageQuery);
        return RestResult.success(follows);
    }

    @PostMapping(value = "fans", name = "关注我的人")
    public RestResult fans(@RequestBody PageQuery pageQuery) {
        IPage<FollowUserVO> fans = followsService.findFans(pageQuery);
        return RestResult.success(fans);
    }

}
