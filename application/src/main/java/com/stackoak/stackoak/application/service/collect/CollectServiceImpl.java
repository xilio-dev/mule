package com.stackoak.stackoak.application.service.collect;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.collect.Collect;
import com.stackoak.stackoak.common.data.collect.CollectSaveRequest;
import com.stackoak.stackoak.repository.collect.CollectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 收藏夹
 * 服务实现类
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

    /**
     * 保存收藏夹
     *
     * @param userId  用户编号
     * @param request 收藏夹数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCollect(String userId, CollectSaveRequest request) {
        //收藏夹ID不为空则更新
        if (StringUtils.hasText(request.id())) {
            LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Collect::getUserId, userId)
                    .eq(Collect::getId, request.id());
            getOneOpt(wrapper)
                    .ifPresentOrElse(
                            collect -> {
                                // 更新字段
                                collect.setName(request.name());
                                collect.setDescription(request.description());
                                collect.setStatus(request.status());
                                if (!updateById(collect)) {
                                    throw new BizException("更新收藏夹失败！");
                                }
                            },
                            () -> {
                                throw new BizException("收藏夹不存在！");
                            }
                    );
        } else {
            Collect collect = new Collect();
            collect.setUserId(userId);
            collect.setName(request.name());
            collect.setStatus(request.status());
            collect.setDescription(request.description());
            save(collect);
        }
    }

    /**
     * 获取用户创建的收藏夹列表
     *
     * @param userId    用户编号
     * @param pageQuery 查询参数
     * @return 收藏夹列表
     */
    @Override
    public Page<Collect> listByPageAndUser(String userId, PageQuery pageQuery) {
        Page<Collect> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, userId);
        wrapper.eq(Collect::getStatus, 1);
        return page(page, wrapper);
    }

    /**
     * 删除收藏夹
     *
     * @param userId    用户编号
     * @param collectId 收藏夹编号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCollect(String userId, String collectId) {
        Collect collect = getCollectByUser(collectId, userId);
        if (ObjectUtils.isEmpty(collect)) {
            throw new BizException("收藏夹不存在！");
        }
        removeById(collectId);
    }
}
