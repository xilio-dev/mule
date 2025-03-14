package com.stackoak.stackoak.application.service.user;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.user.UserBehavior;
import com.stackoak.stackoak.repository.user.UserBehaviorMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户行为记录表 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-14 13:26:02
 */
@Service
public class UserBehaviorServiceImpl extends ServiceImpl<UserBehaviorMapper, UserBehavior> implements IUserBehaviorService {

}
