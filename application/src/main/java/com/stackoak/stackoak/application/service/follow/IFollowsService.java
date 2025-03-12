package com.stackoak.stackoak.application.service.follow;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.follow.FollowPageQuery;
import com.stackoak.stackoak.common.data.follow.FollowRequest;
import com.stackoak.stackoak.common.data.follow.FollowUserVO;
import com.stackoak.stackoak.common.data.follow.Follows;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 14:53:49
 */
public interface IFollowsService extends IService<Follows> {
    /**
     * 获取我关注的人
     *
     * @return 关注列表
     */

    IPage<FollowUserVO> findFollow(FollowPageQuery pageQuery);

    /**
     * 获取我的粉丝
     *
     * @return 粉丝列表
     */
    IPage<FollowUserVO> findFans(FollowPageQuery pageQuery);

    /**
     * 取消关注
     *
     * @param userId 请求参数
     */
    void cancelFollow(String userId);

    /**
     * 关注作者
     *
     * @param userId 请求参数
     */
    void follow(String userId);
}
