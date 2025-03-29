package com.stackoak.stackoak.application.service.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoak.stackoak.application.actors.security.StpKit;

import com.stackoak.stackoak.common.data.user.*;
import com.stackoak.stackoak.repository.user.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public User getCurrentUser() {
        String userId = StpKit.USER.getLoginIdAsString();
        return getById(userId);
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
    @Transactional(rollbackFor = Exception.class)
    public void updateProfile(UpdateProfileRequest request) {
        User user = getById(StpKit.USER.getLoginIdAsString());
        BeanUtils.copyProperties(request, user);
        if (!ObjectUtils.isEmpty(request.getTagIds())) {
            String tags = org.apache.commons.lang3.StringUtils.join(request.getTagIds(), ",");
            user.setTagIds(tags);
        } else {
            user.setTagIds(null);
        }
        updateById(user);
    }

    @Override
    public UpdateProfileRequest getProfile() {
        User user = getById(StpKit.USER.getLoginIdAsString());
        UpdateProfileRequest dto = new UpdateProfileRequest();
        if (StringUtils.hasLength(user.getTagIds())) {
            dto.setTagIds(Arrays.asList(user.getTagIds().split(",")));
        }
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    /**
     * 获取用户详细信息
     *
     * @param userId 用户ID
     * @return 用户详细信息
     */
    @Override
    public UserDetailVo getUserDetail(String userId) {
        //如果当前用户已经登陆，那么需要返回作者与该用户的关系信息
        if (StpKit.USER.isLogin()) {
            String currentUser = StpKit.USER.getLoginIdAsString();
        }
        User byId = getById(userId);
        UserDetailVo vo = new UserDetailVo();
        BeanUtils.copyProperties(byId, vo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCover(UpdateCoverRequest request, String userId) {
        Integer type = request.type();
        User user = new User();
        if (type == 2) {
            user.setTopPhoto(request.cover());
        }
        if (type == 1) {
            user.setBgPhoto(request.cover());
        }
        user.setId(userId);/*更新当前登陆用户作者主页顶部封面*/
        updateById(user);
    }
}
