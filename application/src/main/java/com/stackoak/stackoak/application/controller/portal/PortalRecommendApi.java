package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.recommend.IRecommendService;
import com.stackoak.stackoak.common.data.CommonPageQuery;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "推荐接口")
@RestController
@RequestMapping("/recommend")
public class PortalRecommendApi {
    @Autowired
    private IRecommendService recommendService;

    @PostMapping("article")
    //@FieldFilter(type = Article.class,include = {"id","userId","title"})
    // 根据用户是否登录，返回个性化推荐或默认推荐
    public Result getRecommendations(@RequestBody CommonPageQuery pageQuery) {
        String userId = null;
        if (StpKit.USER.isLogin()){
            userId=StpKit.USER.getLoginIdAsString();
        }
        Page<ArticleBriefVO>req=recommendService.getRecommendation(userId,pageQuery);
        return Result.success(req);
    }
    @PostMapping("similarity-article")
    public Result getArticleSimilarityRecommender(@RequestBody CommonPageQuery pageQuery) {
        String userId = null;
        if (StpKit.USER.isLogin()){
            userId=StpKit.USER.getLoginIdAsString();
        }
        Page<ArticleBriefVO>req=recommendService.getArticleSimilarityRecommender(userId,pageQuery);
        return Result.success(req);
    }



    @PostMapping("user")
    public Result recommendAuthors(
            @RequestBody PageQuery pageQuery) {
      return null;
    }

}
