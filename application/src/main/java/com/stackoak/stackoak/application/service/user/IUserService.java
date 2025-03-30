package com.stackoak.stackoak.application.service.user;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.user.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
public interface IUserService extends IService<User> {

    User getCurrentUser();

    User getByEmail(@Email String email);

    /**
     * 更新用户个人信息
     *
     * @param request 更新个人信息
     */
    void updateProfile(UpdateProfileRequest request);

    UpdateProfileRequest getProfile();

    /**
     * 获取用户详细信息
     * @param userId 用户ID
     * @return 用户详细信息
     */
    UserDetailVo getUserDetail(String userId);

    void updateCover(UpdateCoverRequest request, String userId);

    /**
     * 获取我拉黑的作者列表
     * @param request 分页请求
     * @param userId 当前登陆用户
     * @return 黑名单列表
     */
    Page<User> dislike(@Valid PageQuery request, String userId);
}
