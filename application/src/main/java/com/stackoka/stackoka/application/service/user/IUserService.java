package com.stackoka.stackoka.application.service.user;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoka.stackoka.common.data.user.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
public interface IUserService extends IService<User> {

    User getCurrentUser();

}
