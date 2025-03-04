package com.stackoak.stackoak.application.service.follow;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.application.service.user.IUserService;
import com.stackoak.stackoak.common.data.PageQuery;
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
     * 获取我关注的人
     *
     * @return 关注列表
     */
    @Override
    public IPage<FollowUserVO> findFollow(PageQuery pageQuery) {
        Page<FollowUserVO> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        page.setRecords(followsMapper.selectFollow(page, "1", "3"));
        return page;
    }

    /**
     * 获取我的粉丝
     *
     * @return 粉丝列表
     */
    @Override
    public IPage<FollowUserVO> findFans(PageQuery pageQuery) {
        Page<FollowUserVO> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        return followsMapper.selectFans(page, "1", null);
    }

    /**
     * 取消关注
     *
     * @param followRequest 请求参数
     */
    @Override
    public void cancelFollow(FollowRequest followRequest) {
        String currentUser = "1";//todo 临时测试
        //检查是否已经关注了作者，如果关注了则删除 需要检查目标用户的状态，例如是否被禁
        LambdaQueryWrapper<Follows> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follows::getTargetUserId, followRequest.getUserId());
        wrapper.eq(Follows::getUserId, currentUser);
        followsMapper.delete(wrapper);
    }

    /**
     * 关注作者
     *
     * @param followRequest 请求参数
     */
    @Override
    public void follow(FollowRequest followRequest) {
        String currentUser = "1";//todo 临时测试
        //不能够关注自己
        if (currentUser.equals(followRequest.getUserId())) {
            throw new BizException("不能关注自己");
        }
        //检查关注的人是否存在
        User user = userService.getById(followRequest.getUserId());
        if (ObjectUtils.isEmpty(user)) {
            throw new BizException("期望关注的作者不存在！");
        }
        //todo 检查被关注作者的状态是否正常

        //检查是否已经关注了作者，没有关注再关注
        LambdaQueryWrapper<Follows> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follows::getTargetUserId, followRequest.getUserId());
        wrapper.eq(Follows::getUserId, currentUser);
        Follows follows = getOne(wrapper);
        if (!ObjectUtils.isEmpty(follows)) {
            throw new BizException("不能重复关注！");
        }
        //关注用户
        save(new Follows(currentUser, followRequest.getUserId()));
    }
}
