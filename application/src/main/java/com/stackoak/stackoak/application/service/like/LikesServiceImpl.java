package com.stackoak.stackoak.application.service.like;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.likes.LikeTypeEnum;
import com.stackoak.stackoak.common.data.likes.Like;
import com.stackoak.stackoak.repository.likes.LikesMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 22:02:39
 */
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Like> implements ILikesService {

    @Override
    public Like getLike(String userId, String targetId, LikeTypeEnum likeTypeEnum) {
        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Like::getUserId, userId);
        wrapper.eq(Like::getTargetId, targetId);
        wrapper.eq(Like::getType, likeTypeEnum.getType());
        return getOne(wrapper);
    }
}
