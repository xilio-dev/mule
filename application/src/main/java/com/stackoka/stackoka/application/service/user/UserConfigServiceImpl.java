package com.stackoka.stackoka.application.service.user;


import com.stackoka.stackoka.common.data.user.UserConfig;
import com.stackoka.stackoka.repository.user.UserConfigMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
@Service
public class UserConfigServiceImpl extends ServiceImpl<UserConfigMapper, UserConfig> implements IUserConfigService {

}
