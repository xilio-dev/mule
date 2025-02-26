package com.stackoka.stackoka.application.controller;

import com.stackoka.stackoka.application.service.tag.ITagService;
import com.stackoka.stackoka.common.data.column.Column;
import com.stackoka.stackoka.common.data.tag.Tag;
import com.stackoka.stackoka.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author stackoka.com
 * @since 2025-02-15
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private ITagService tagService;

    @GetMapping("list")
    public Result list() {
        List<Tag> list = tagService.list();
        return Result.success(list);
    }
}
