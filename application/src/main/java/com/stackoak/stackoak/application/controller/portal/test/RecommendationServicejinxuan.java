package com.stackoak.stackoak.application.controller.portal.test;

import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.user.UserBehavior;
import com.stackoak.stackoak.repository.article.ArticleMapper;
import com.stackoak.stackoak.repository.user.UserBehaviorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationServicejinxuan {

    @Autowired
    private UserBehaviorMapper behaviorRepository;

    @Autowired
    private ArticleMapper articleRepository;

    // 行为评分规则
    private static final Map<String, Integer> ACTION_SCORES = new HashMap<>() {{
        put("LIKE", 5);
        put("FAVORITE", 4);
        put("READ", 3);
        put("VIEW", 2);
        put("COMMENT", 1);
    }};

    // 计算文章热度得分
    private double calculateHotnessScore(Article article) {
        // 加权公式：热度 = 0.4 * 浏览量 + 0.4 * 点赞数 + 0.2 * 评论数
        return 0.4 * article.getViewCount() + 0.4 * article.getLikeCount() + 0.2 * article.getCommentCount();
    }

    // 用户-文章评分矩阵（ID 改为 String）
    private Map<String, Map<String, Integer>> buildUserArticleMatrix() {
        List<UserBehavior> behaviors = behaviorRepository.selectList(null);
        Map<String, Map<String, Integer>> matrix = new HashMap<>();

        for (UserBehavior behavior : behaviors) {
            matrix.computeIfAbsent(behavior.getUserId(), k -> new HashMap<>())
                    .merge(behavior.getArticleId(), ACTION_SCORES.get(behavior.getBehaviorType()), Integer::sum);
        }
        return matrix;
    }

    // 用户相似度（余弦相似度，ID 改为 String）
    private double cosineSimilarity(Map<String, Integer> user1Ratings, Map<String, Integer> user2Ratings) {
        double dotProduct = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (String articleId : user1Ratings.keySet()) {
            if (user2Ratings.containsKey(articleId)) {
                dotProduct += user1Ratings.get(articleId) * user2Ratings.get(articleId);
            }
            norm1 += Math.pow(user1Ratings.get(articleId), 2);
        }
        for (String articleId : user2Ratings.keySet()) {
            norm2 += Math.pow(user2Ratings.get(articleId), 2);
        }
        return (norm1 == 0 || norm2 == 0) ? 0.0 : dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    // 获取精选内容（ID 改为 String）
    public List<Article> getFeaturedArticles(String userId, int topN) {
        List<Article> allArticles = articleRepository.selectList(null);
        Map<String, Double> scores = new HashMap<>();

        if (userId == null) {
            // 未登录用户：基于热度排序
            for (Article article : allArticles) {
                double hotnessScore = calculateHotnessScore(article);
                scores.put(article.getId(), hotnessScore);
            }
        } else {
            // 已登录用户：热度 + 用户行为偏好
            Map<String, Map<String, Integer>> matrix = buildUserArticleMatrix();
            Map<String, Integer> targetUserRatings = matrix.getOrDefault(userId, new HashMap<>());

            // 1. 热度得分
            for (Article article : allArticles) {
                double hotnessScore = calculateHotnessScore(article);
                scores.put(article.getId(), hotnessScore * 0.5); // 热度权重 0.5
            }

            // 2. 行为推荐得分
            Map<String, Double> behaviorScores = new HashMap<>();
            Map<String, Double> similarities = new HashMap<>();
            for (String otherUserId : matrix.keySet()) {
                if (!otherUserId.equals(userId)) {
                    double similarity = cosineSimilarity(targetUserRatings, matrix.get(otherUserId));
                    similarities.put(otherUserId, similarity);
                }
            }

            List<String> similarUsers = similarities.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .limit(topN)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            for (String similarUserId : similarUsers) {
                double similarity = similarities.get(similarUserId);
                Map<String, Integer> similarUserRatings = matrix.get(similarUserId);
                for (String articleId : similarUserRatings.keySet()) {
                    behaviorScores.merge(articleId, similarity * similarUserRatings.get(articleId), Double::sum);
                }
            }

            // 合并行为得分（权重 0.5）
            for (String articleId : behaviorScores.keySet()) {
                scores.merge(articleId, behaviorScores.get(articleId) * 0.5, Double::sum);
            }
        }

        // 返回 topN 篇精选文章
        return scores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(topN)
                .map(entry -> articleRepository.selectById(entry.getKey()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
