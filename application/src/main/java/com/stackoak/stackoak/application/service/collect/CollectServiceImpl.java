package com.stackoak.stackoak.application.service.collect;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.collect.*;
import com.stackoak.stackoak.repository.collect.ArticleCollectMapper;
import com.stackoak.stackoak.repository.collect.CollectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    @Autowired
    private ArticleCollectMapper articleCollectMapper;

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
                                BizException.exprNull(!updateById(collect), "更新收藏夹失败！");
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
     * @param pageQuery 查询参数
     * @return 收藏夹列表
     */
    @Override
    public Page<Collect> listByPageAndUser(CommonPageQuery pageQuery) {
        Page<Collect> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, pageQuery.getId());
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
        BizException.noNull(collect, "收藏夹不存在!");
        removeById(collectId);
    }

    @Override
    public Page<CollectDTO> listByUser(CommonPageQuery pageQuery, String userId) {
        String articleId = pageQuery.getId();
        BizException.noEmpty(articleId, "文章编号不能为空!");
        return baseMapper.selectByUser(Page.of(pageQuery.getCurrent(), pageQuery.getSize()), articleId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addArticleToCollect(String userId, SaveArticleToCollectRequest request) {
        /*批量插入*/
        List<ArticleCollect> batches = new ArrayList<>();
        request.ids().forEach(collectId -> {
            Collect collect = getCollectByUser(collectId, userId);
            BizException.noNull(collect, "收藏夹不存在!");
            batches.add(new ArticleCollect(request.aid(), collectId));
        });
        try {
            articleCollectMapper.insert(batches);
        } catch (Exception e) {
            throw new BizException("收藏夹已存在该文章！");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticleFromCollect(String userId, SaveArticleToCollectRequest request) {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getUserId, userId);
        wrapper.in(Collect::getId, request.ids());
        List<Collect> collects = list(wrapper);
        BizException.exprNull(collects.isEmpty() || collects.size() != request.ids().size(), "收藏夹不存在！");

        LambdaQueryWrapper<ArticleCollect> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(ArticleCollect::getArticleId, request.aid());
        deleteWrapper.in(ArticleCollect::getCollectId, request.ids());
        articleCollectMapper.delete(deleteWrapper);
    }
}
