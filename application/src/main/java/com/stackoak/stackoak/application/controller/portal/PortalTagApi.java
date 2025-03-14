package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.service.tag.ITagService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.tag.Tag;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-15
 */
@RestController
@RequestMapping("/tag")
public class PortalTagApi {
    @Autowired
    private ITagService tagService;

    @PostMapping("recommend-tags")
    @FieldFilter(type = Tag.class,exclude = "userId")
    public Result recommendTags(@RequestBody PageQuery query) {
        return Result.success(tagService.sysTags(query));
    }
}
