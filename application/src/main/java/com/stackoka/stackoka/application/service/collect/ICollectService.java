package com.stackoka.stackoka.application.service.collect;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoka.stackoka.common.data.collect.Collect;

/**
 * <p>
 * 收藏夹
 * 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
public interface ICollectService extends IService<Collect> {
    /**
     * 获取用户当个收藏夹信息
     *
     * @param collectId 收藏夹编号
     * @param userId    用户编号
     * @return 收藏夹信息
     */
    Collect getCollectByUser(String collectId, String userId);
}
