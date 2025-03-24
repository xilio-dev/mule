package com.stackoak.stackoak.application.controller.portal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.application.service.user.IUserBehaviorService;
import com.stackoak.stackoak.application.service.user.IUserService;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.common.data.user.UserBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorRecommendationService {

    @Autowired
    @Lazy
    private IUserBehaviorService userBehaviorService;
    @Autowired
    @Lazy
    private IArticleService articleService;
    @Autowired
    @Lazy
    private IUserService userService;

    private static final Map<String, Float> BEHAVIOR_SCORES = new HashMap<>();
    static {
        BEHAVIOR_SCORES.put("VIEW", 2.0f);
        BEHAVIOR_SCORES.put("LIKE", 3.0f);
        BEHAVIOR_SCORES.put("COLLECT", 4.0f);
        BEHAVIOR_SCORES.put("COMMENT", 5.0f);
        BEHAVIOR_SCORES.put("FOLLOW", 5.0f);
    }

    public List<User> recommendAuthors(String userId, int page, int size) {
        int offset = page * size;
        if (userId != null && !userId.isEmpty()) {
            return recommendForLoggedInUser(userId, offset, size);
        } else {
            return recommendForGuest(offset, size);
        }
    }

    private List<User> recommendForLoggedInUser(String userId, int offset, int size) {
        // 获取用户行为
        LambdaQueryWrapper<UserBehavior> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBehavior::getUserId,userId);
        List<UserBehavior> behaviors = userBehaviorService.list(wrapper);

        // 计算每个作者的得分
        Map<String, Float> authorScores = new HashMap<>();
        Set<String> followedAuthors = new HashSet<>();

        for (UserBehavior behavior : behaviors) {
            String articleId = behavior.getArticleId();
            Article article = articleService.getById(articleId);
            if (article == null) continue;

            String authorId = article.getUserId();
            String behaviorType = behavior.getBehaviorType();

            // 记录已关注的作者
            if ("FOLLOW".equals(behaviorType)) {
                followedAuthors.add(authorId);
                continue;
            }

            // 计算得分
            float score = BEHAVIOR_SCORES.getOrDefault(behaviorType, 0.0f);
            authorScores.put(authorId, authorScores.getOrDefault(authorId, 0.0f) + score);
        }

        // 过滤已关注的作者并排序
        List<Map.Entry<String, Float>> sortedAuthors = authorScores.entrySet().stream()
                .filter(entry -> !followedAuthors.contains(entry.getKey()))
                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())
                .collect(Collectors.toList());

        // 分页获取作者信息
        List<String> authorIds = sortedAuthors.stream()
                .skip(offset)
                .limit(size)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return userService.listByIds(authorIds);
    }

    private List<User> recommendForGuest(int offset, int size) {
        // 获取所有文章
        List<Article> articles = articleService.list();

        // 计算每个作者的互动量
        Map<String, Integer> authorInteraction = new HashMap<>();
        for (Article article : articles) {
            String authorId = article.getUserId();
            int interaction = article.getLikeCount() + article.getViewCount() +
                             article.getCommentCount() + article.getCollectCount();
            authorInteraction.put(authorId, authorInteraction.getOrDefault(authorId, 0) + interaction);
        }

        // 按互动量排序
        List<String> sortedAuthorIds = authorInteraction.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .skip(offset)
                .limit(size)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return userService.listByIds(sortedAuthorIds);
    }
}
