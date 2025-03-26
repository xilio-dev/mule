package com.stackoak.stackoak.application.service.collect;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.collect.Collect;
import com.stackoak.stackoak.common.data.collect.CollectSaveRequest;

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
     * 获取用户收藏夹信息
     *
     * @param collectId 收藏夹编号
     * @param userId    用户编号
     * @return 收藏夹信息
     */
    Collect getCollectByUser(String collectId, String userId);

    /**
     * 保存收藏夹
     * @param userId 用户编号
     * @param request 收藏夹数据
     */
    void saveCollect(String userId, CollectSaveRequest request);

    /**
     * 获取用户创建的收藏夹列表

     * @param pageQuery 查询参数
     * @return 收藏夹列表
     */
    Page<Collect> listByPageAndUser(CommonPageQuery pageQuery);

    /**
     * 删除收藏夹
     * @param userId 用户编号
     * @param collectId 收藏夹编号
     */
    void deleteCollect(String userId, String collectId);

    Page<Collect>  listByUser(PageQuery pageQuery, String userId);

}
