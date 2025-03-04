package com.stackoka.stackoka.application.service.follow;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoka.stackoka.common.data.PageQuery;
import com.stackoka.stackoka.common.data.follow.FollowRequest;
import com.stackoka.stackoka.common.data.follow.FollowUserVO;
import com.stackoka.stackoka.common.data.follow.Follows;

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

    IPage<FollowUserVO> findFollow(PageQuery pageQuery);

    /**
     * 获取我的粉丝
     *
     * @return 粉丝列表
     */
    IPage<FollowUserVO> findFans(PageQuery pageQuery);

    /**
     * 取消关注
     *
     * @param followRequest 请求参数
     */
    void cancelFollow(FollowRequest followRequest);

    /**
     * 关注作者
     *
     * @param followRequest 请求参数
     */
    void follow(FollowRequest followRequest);
}
