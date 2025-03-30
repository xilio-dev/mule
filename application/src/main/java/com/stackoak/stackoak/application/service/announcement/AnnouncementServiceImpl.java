package com.stackoak.stackoak.application.service.announcement;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stackoak.stackoak.common.data.announcement.Announcement;
import com.stackoak.stackoak.repository.announcement.AnnouncementMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-30 22:06:08
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements IAnnouncementService {

}
