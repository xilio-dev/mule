package com.stackoak.stackoak.application.service.user;


import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.repository.user.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getCurrentUser() {
        return null;
    }
}
