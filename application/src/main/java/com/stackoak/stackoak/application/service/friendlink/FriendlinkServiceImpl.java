package com.stackoak.stackoak.application.service.friendlink;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.friendlink.FriendLink;
import com.stackoak.stackoak.repository.friendlink.FriendLinkMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-28 15:46:24
 */
@Service
public class FriendlinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements IFriendLinkService {

    /**
     * @return
     */
    @Override
    public List<FriendLink> listByPage() {
        LambdaQueryWrapper<FriendLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FriendLink::getStatus, 1);
        return list(wrapper);
    }
}
