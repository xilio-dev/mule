package com.stackoak.stackoak.application.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.user.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
public interface IUserService extends IService<User> {

    User getCurrentUser();

}
