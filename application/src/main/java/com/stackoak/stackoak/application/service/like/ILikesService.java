package com.stackoak.stackoak.application.service.like;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.likes.LikeTypeEnum;
import com.stackoak.stackoak.common.data.likes.Like;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 22:02:39
 */
public interface ILikesService extends IService<Like> {

    boolean isLiked(String userId, String targetId, LikeTypeEnum likeType);
}
