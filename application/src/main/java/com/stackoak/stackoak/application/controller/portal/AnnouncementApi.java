package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.service.announcement.IAnnouncementService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.announcement.Announcement;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  公告接口
 * </p>
 *
 * @author stackoak.com
 * @since 2025-03-30 22:06:08
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementApi {
    @Autowired
    private IAnnouncementService announcementService;
    @PostMapping("list")
    public Result getAnnouncements(@RequestBody PageQuery pageQuery) {
      return Result.success(announcementService.getAnnouncements(pageQuery));
    }

    @PostMapping("type/{typeCode}")
    public Result getAnnouncementsByType(@RequestBody CommonPageQuery pageQuery) {
        return Result.success(announcementService.getAnnouncementsByType(pageQuery));
    }

    @GetMapping(value = "detail/{id}",name = "公告详情")
    public Result detail(@PathVariable String id) {
        return Result.success(announcementService.getAnnouncementById(id)) ;
    }
}
