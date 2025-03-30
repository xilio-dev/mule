package com.stackoak.stackoak.application.service.announcement;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.announcement.Announcement;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-30 22:06:08
 */
public interface IAnnouncementService extends IService<Announcement> {
    /**
     * 查询所有的公告
     *
     * @param pageQuery 分页参数
     * @return 所有已发布公告列表
     */
    Page<Announcement> getAnnouncements(PageQuery pageQuery);

    /**
     * 通过类型获取已发布公告列表 【已发布】
     *
     * @param pageQuery 分页查询
     * @return 公告列表
     */
    Page<Announcement> getAnnouncementsByType(CommonPageQuery pageQuery);

    /**
     * 获取已发布公告详情
     *
     * @param id 公告id
     * @return 详情
     */
    Announcement getAnnouncementById(String id);
}
