package com.stackoak.stackoak.application.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.user.LoginUser;
import com.stackoak.stackoak.common.data.user.UpdateProfileRequest;
import com.stackoak.stackoak.common.data.user.User;
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

    LoginUser getCurrentUser();

    User getByEmail(@Email String email);

    /**
     * 更新用户个人信息
     *
     * @param request 更新个人信息
     */
    void updateProfile(UpdateProfileRequest request);

    UpdateProfileRequest getProfile();

}
