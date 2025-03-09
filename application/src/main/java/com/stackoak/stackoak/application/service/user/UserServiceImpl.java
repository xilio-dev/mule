package com.stackoak.stackoak.application.service.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.common.data.user.LoginUser;
import com.stackoak.stackoak.common.data.user.UpdateProfileRequest;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.repository.user.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public LoginUser getCurrentUser() {
        Object loginId = StpKit.USER.getLoginId();
        User byId = getById(String.valueOf(loginId));
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(byId, loginUser);
        loginUser.setUserId(byId.getId());
        return loginUser;
    }

    /**
     * @param email 邮箱账号
     * @return 用户信息
     */
    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return getOne(wrapper);
    }

    @Override
    public void updateProfile(UpdateProfileRequest request) {
        User user = getById(StpKit.USER.getLoginIdAsString());
        BeanUtils.copyProperties(request, user);
        updateById(user);
    }

    @Override
    public UpdateProfileRequest getProfile() {
        User user = getById(StpKit.USER.getLoginIdAsString());
        UpdateProfileRequest dto = new UpdateProfileRequest();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
