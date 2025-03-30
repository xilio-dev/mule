package com.stackoak.stackoak.application.service.announcement;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.announcement.Announcement;
import com.stackoak.stackoak.common.data.announcement.AnnouncementType;
import com.stackoak.stackoak.repository.announcement.AnnouncementMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-30 22:06:08
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements IAnnouncementService {

    @Override
    public Page<Announcement> getAnnouncements(PageQuery pageQuery) {
        Page<Announcement> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        LambdaQueryWrapper<Announcement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Announcement::getIsPublished, true);
        wrapper.orderByDesc(Announcement::getCreatedAt);
        return page(page, wrapper);
    }

    @Override
    public Page<Announcement> getAnnouncementsByType(CommonPageQuery pageQuery) {
        Integer type = pageQuery.getType();
        Page<Announcement> page = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        AnnouncementType announcementType = AnnouncementType.fromCode(type);
        BizException.noNull(announcementType, "必须指定正确的通知类型！");
        LambdaQueryWrapper<Announcement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Announcement::getIsPublished, true);
        wrapper.eq(Announcement::getType, announcementType.getCode());
        wrapper.orderByDesc(Announcement::getCreatedAt);
        return page(page, wrapper);
    }

    @Override
    public Announcement getAnnouncementById(String id) {
        LambdaQueryWrapper<Announcement> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Announcement::getId, id);
        wrapper.eq(Announcement::getIsPublished, true);
        return getOne(wrapper);
    }
}
