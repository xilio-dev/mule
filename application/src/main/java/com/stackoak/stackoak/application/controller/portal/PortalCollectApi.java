package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收藏夹
 * 收藏夹控制器
 * </p>
 *
 * @author stackoak.com
 * @since 2025-02-27 23:00:30
 */
@Tag(name = "收藏夹")
@RestController
@RequestMapping("/collect")
public class PortalCollectApi {
    @PostMapping(value = "list", name = "收藏夹列表")
    public Result list() {
        return null;
    }


    @PostMapping(value = "save", name = "保存收藏夹")
    public Result save() {
        return null;
    }

    @DeleteMapping(value = "delete", name = "删除收藏夹")
    public Result delete() {
        return null;
    }

    @PostMapping(value = "add-article-to-collect", name = "添加文章到收藏夹")
    public Result addArticleToCollect() {
        return null;
    }

    @DeleteMapping(value = "del-article-from-collect", name = "从收藏夹中删除文章")
    public Result delArticleFromCollect() {
        return null;
    }


}
