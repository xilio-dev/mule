package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.service.recommend.IRecommendService;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "推荐系统")
@RestController
@RequestMapping("/recommend")
public class PortalRecommendApi {
    @Autowired
    private IRecommendService recommendService;

    @PostMapping(value = "/user", name = "根据用户喜好精准推荐首页文章")
    @Operation(description = "根据用户喜好精准推荐首页文章")
    public Result homeRecommendByUserId(@RequestBody RecommendByUserQuery query) {
        return Result.success(recommendService.homeRecommendByUserId(query));
    }
}
