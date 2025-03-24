package com.stackoak.stackoak.application.controller.portal;

import com.stackoak.stackoak.application.RecommendationService;
import com.stackoak.stackoak.application.actors.cut.FieldFilter;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.common.message.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommender")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private AuthorRecommendationService authorRecommendationService;

    @GetMapping("/authors")
    public ResponseEntity<List<User>> recommendAuthors(
            @RequestParam(required = false) String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<User> authors = authorRecommendationService.recommendAuthors(userId, page, size);
        return ResponseEntity.ok(authors);
    }
    @GetMapping("/articles")
    @FieldFilter(type = Article.class,include = {"id","userId","title"})
    // 根据用户是否登录，返回个性化推荐或默认推荐
    public Result getRecommendations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Article> recommendations;
            // 如果用户已登录，则返回个性化推荐
            if (StpKit.USER.isLogin()) {
                String userId = StpKit.USER.getLoginIdAsString();
                recommendations = recommendationService.getPersonalizedRecommendations(userId, page, size);
            } else {
                // 如果用户未登录，则返回默认推荐
                recommendations = recommendationService.getDefaultRecommendations(page, size);
            }
            return Result.success(recommendations);
        } catch (Exception e) {
            // 如果发生异常，则返回500错误
            return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }
}
