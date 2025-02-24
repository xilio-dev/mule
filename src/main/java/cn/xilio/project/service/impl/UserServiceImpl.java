package cn.xilio.project.service.impl;

import cn.xilio.project.bo.User;
import cn.xilio.project.mapper.UserMapper;
import cn.xilio.project.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xilio.cn
 * @since 2025-02-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
