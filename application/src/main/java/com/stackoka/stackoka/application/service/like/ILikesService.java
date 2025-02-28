package com.stackoka.stackoka.application.service.like;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoka.stackoka.common.data.likes.LikeTypeEnum;
import com.stackoka.stackoka.common.data.likes.Likes;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 22:02:39
 */
public interface ILikesService extends IService<Likes> {

    Likes getLike(String userId, String targetId, LikeTypeEnum likeType);
}
