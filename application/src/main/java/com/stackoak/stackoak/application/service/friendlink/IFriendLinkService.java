package com.stackoak.stackoak.application.service.friendlink;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.friendlink.FriendLink;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-28 15:46:24
 */
public interface IFriendLinkService extends IService<FriendLink> {

    List<FriendLink> listByPage();

}
