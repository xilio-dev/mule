package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.actors.security.SaUserCheckLogin;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.collect.ICollectService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.collect.SaveArticleToCollectRequest;
import com.stackoak.stackoak.common.data.collect.CollectSaveRequest;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private ICollectService collectService;

    @PostMapping(value = "list", name = "获取用户收藏夹列表")
    public Result list(@RequestBody CommonPageQuery pageQuery) {
        return Result.success(collectService.listByPageAndUser(pageQuery));
    }

    @PostMapping(value = "visit_collect", name = "获取访问者用户收藏夹列表")
    public Result visitCollect(@RequestBody PageQuery pageQuery) {
        String userId = StpKit.USER.getLoginIdAsString();
        return Result.success(collectService.listByUser(pageQuery, userId));
    }

    @PostMapping(value = "save", name = "保存收藏夹")
    @SaUserCheckLogin
    public Result save(@RequestBody CollectSaveRequest request) {
        String userId = StpKit.USER.getLoginIdAsString();
        collectService.saveCollect(userId, request);
        return Result.success();
    }

    @GetMapping(value = "get", name = "获取收藏夹信息")
    public Result get(@RequestParam String id) {
        return Result.success(collectService.getById(id));
    }

    @DeleteMapping(value = "del", name = "删除收藏夹")
    public Result delete(@RequestParam("id") String id) {
        String userId = StpKit.USER.getLoginIdAsString();
        collectService.deleteCollect(userId, id);
        return Result.success();
    }

    @PostMapping(value = "add-article-to-collect", name = "添加文章到收藏夹")
    public Result addArticleToCollect(@RequestBody SaveArticleToCollectRequest request) {
        String userId = StpKit.USER.getLoginIdAsString();
        collectService.addArticleToCollect(userId, request);
        return Result.success();
    }

    @PutMapping(value = "del-article-from-collect", name = "从收藏夹中删除文章")
    public Result delArticleFromCollect(@RequestBody SaveArticleToCollectRequest request) {
        String userId = StpKit.USER.getLoginIdAsString();
        collectService.deleteArticleFromCollect(userId, request);
        return Result.success();
    }
}
