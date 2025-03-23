package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.service.recommend.IRecommendService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "推荐接口")
@RestController
@RequestMapping("/recommend")
public class PortalRecommendApi {
    @Autowired
    private IRecommendService recommendService;

    @PostMapping(value = "/article", name = "根据用户喜好精准推荐首页文章")
    @Operation(description = "根据用户喜好精准推荐首页文章")
    public Result getHomeArticleRecommend(@RequestBody PageQuery query) {
        return Result.success(recommendService.getHomeArticleRecommend(query));
    }

    @PostMapping(value = "/author", name = "首页作者推荐")
    @Operation(description = "根据用户喜好推荐作者-未关注的")
    public Result homeAuthorRecommendByUserId(@RequestBody RecommendByUserQuery query) {
        return Result.success(recommendService.homeAuthorRecommendByUserId(query));
    }

    @PostMapping(value = "/related-articles", name = "相关文章推荐")
    @Operation(description = "推荐与阅读文章相关的文章")
    public Result  getRelatedArticles(@RequestBody RecommendByUserQuery query) {
        return Result.success(recommendService.getRelatedArticles(query));
    }



}
