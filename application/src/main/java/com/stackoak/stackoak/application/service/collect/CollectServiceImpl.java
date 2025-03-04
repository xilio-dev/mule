package com.stackoak.stackoak.application.service.collect;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.collect.Collect;
import com.stackoak.stackoak.repository.collect.CollectMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收藏夹
 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {


    /**
     * 获取用户当个收藏夹信息
     *
     * @param collectId 收藏夹编号
     * @param userId    用户编号
     * @return 收藏夹信息
     */
    @Override
    public Collect getCollectByUser(String collectId, String userId) {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, userId);
        wrapper.eq(Collect::getId, collectId);
        return getOne(wrapper);
    }
}
