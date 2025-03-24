package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.recommend.IRecommendService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "推荐接口")
@RestController
@RequestMapping("/recommend")
public class PortalRecommendApi {
    @Autowired
    private IRecommendService recommendService;

    @PostMapping("article")
    //@FieldFilter(type = Article.class,include = {"id","userId","title"})
    // 根据用户是否登录，返回个性化推荐或默认推荐
    public Result getRecommendations(@RequestBody PageQuery pageQuery) {
        try {
            Page<Article> recommendations;
            // 如果用户已登录，则返回个性化推荐
            if (StpKit.USER.isLogin()) {
                String userId = StpKit.USER.getLoginIdAsString();
                recommendations = recommendService.getPersonalizedArticleRecommendations(userId, pageQuery);
            } else {
                // 如果用户未登录，则返回默认推荐
                recommendations = recommendService.getDefaultArticleRecommendations(pageQuery);
            }
            return Result.success(recommendations);
        } catch (Exception e) {
            // 如果发生异常，则返回500错误
            return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }



}
