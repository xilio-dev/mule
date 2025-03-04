package com.stackoak.stackoak.application.service.like;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.likes.LikeTypeEnum;
import com.stackoak.stackoak.common.data.likes.Likes;
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
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements ILikesService {

    @Override
    public Likes getLike(String userId, String targetId, LikeTypeEnum likeTypeEnum) {
        LambdaQueryWrapper<Likes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Likes::getUserId, userId);
        wrapper.eq(Likes::getTargetId, targetId);
        wrapper.eq(Likes::getType, likeTypeEnum.getType());
        return getOne(wrapper);
    }
}
