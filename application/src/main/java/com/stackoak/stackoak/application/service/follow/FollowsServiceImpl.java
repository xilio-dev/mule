package com.stackoak.stackoak.application.service.follow;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.application.service.user.IUserService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.follow.FollowPageQuery;
import com.stackoak.stackoak.common.data.follow.FollowRequest;
import com.stackoak.stackoak.common.data.follow.FollowUserVO;
import com.stackoak.stackoak.common.data.follow.Follows;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.repository.follow.FollowsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-04 14:53:49
 */
@Service
public class FollowsServiceImpl extends ServiceImpl<FollowsMapper, Follows> implements IFollowsService {
    @Autowired
    private FollowsMapper followsMapper;
    @Autowired
    private IUserService userService;

    /**
     * 获取我关注的人以及访问者与这些粉丝的关系
     *
     * @return 关注列表
     */
    @Override
    public IPage<FollowUserVO> findFollow(FollowPageQuery pageQuery) {
        Page<FollowUserVO> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        String userId=null;
        if (StpKit.USER.isLogin()) {
            userId=StpKit.USER.getLoginIdAsString();
        }
        page.setRecords(followsMapper.selectFollow(page, pageQuery.getAuthorId(), userId));
        return page;
    }

    /**
     * 获取我的粉丝
     *
     * @return 粉丝列表
     */
    @Override
    public IPage<FollowUserVO> findFans(FollowPageQuery pageQuery) {
        Page<FollowUserVO> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        String userId=null;
        if (StpKit.USER.isLogin()) {
            userId=StpKit.USER.getLoginIdAsString();
        }
        return followsMapper.selectFans(page, pageQuery.getAuthorId(), userId);
    }

    /**
     * 取消关注
     *
     * @param userId 请求参数
     */
    @Override
    public void cancelFollow(String userId) {
        String currentUser = StpKit.USER.getLoginIdAsString();
        //检查是否已经关注了作者，如果关注了则删除 需要检查目标用户的状态，例如是否被禁
        LambdaQueryWrapper<Follows> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follows::getTargetUserId, userId);
        wrapper.eq(Follows::getUserId, currentUser);
        followsMapper.delete(wrapper);
    }

    /**
     * 关注作者
     *
     * @param userId 请求参数
     */
    @Override
    public void follow(String userId) {
        String currentUser = StpKit.USER.getLoginIdAsString();
        //不能够关注自己
        if (currentUser.equals(userId)) {
            throw new BizException("不能关注自己");
        }
        //检查关注的人是否存在
        User user = userService.getById(userId);
        if (ObjectUtils.isEmpty(user)) {
            throw new BizException("期望关注的作者不存在！");
        }
        //todo 检查被关注作者的状态是否正常

        //检查是否已经关注了作者，没有关注再关注
        LambdaQueryWrapper<Follows> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follows::getTargetUserId,userId);
        wrapper.eq(Follows::getUserId, currentUser);
        Follows follows = getOne(wrapper);
        if (!ObjectUtils.isEmpty(follows)) {
            throw new BizException("不能重复关注！");
        }
        //关注用户
        save(new Follows(currentUser, userId));
    }
}
