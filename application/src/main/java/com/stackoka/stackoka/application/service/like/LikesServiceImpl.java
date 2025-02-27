package com.stackoka.stackoka.application.service.like;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoka.stackoka.common.data.likes.Likes;
import com.stackoka.stackoka.repository.likes.LikesMapper;
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
    public Likes getLike(String userId, String articleId) {
        LambdaQueryWrapper<Likes> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Likes::getUserId, userId);
        wrapper.eq(Likes::getTargetId, articleId);
        return getOne(wrapper);
    }
}
