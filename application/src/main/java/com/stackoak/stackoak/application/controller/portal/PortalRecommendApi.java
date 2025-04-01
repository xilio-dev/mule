package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.recommend.IRecommendService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.common.message.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return null;
    }

    @PostMapping("author")
    public Result recommendAuthors(
            @RequestBody PageQuery pageQuery) {
      return null;
    }

}
