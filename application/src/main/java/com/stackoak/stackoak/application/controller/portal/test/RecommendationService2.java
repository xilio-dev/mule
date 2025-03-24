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
public class RecommendationService2 {

    @Autowired
    private UserBehaviorMapper behaviorMapper;

    @Autowired
    private ArticleMapper articleMapper;

    private static final Map<String, Integer> ACTION_SCORES = new HashMap<>() {{
        put("LIKE", 5);
        put("FAVORITE", 4);
        put("READ", 3);
        put("VIEW", 2);
        put("COMMENT", 1);
    }};

    // 计算 TF-IDF
    private Map<String, Double> computeTFIDF(String text, List<String> allTexts) {
        Map<String, Integer> termFreq = new HashMap<>();
        String[] words = text.toLowerCase().split("\\s+");
        for (String word : words) {
            termFreq.merge(word, 1, Integer::sum);
        }

        Map<String, Double> tfidf = new HashMap<>();
        int docCount = allTexts.size();
        for (String word : termFreq.keySet()) {
            double tf = (double) termFreq.get(word) / words.length;
            long docFreq = allTexts.stream().filter(t -> t.contains(word)).count();
            double idf = Math.log((double) docCount / (1 + docFreq));
            tfidf.put(word, tf * idf);
        }
        return tfidf;
    }

    // 手动计算余弦相似度
    private double computeCosineSimilarity(Map<String, Double> vector1, Map<String, Double> vector2) {
        double dotProduct = 0.0, norm1 = 0.0, norm2 = 0.0;
        for (String key : vector1.keySet()) {
            if (vector2.containsKey(key)) {
                dotProduct += vector1.get(key) * vector2.get(key);
            }
            norm1 += Math.pow(vector1.get(key), 2);
        }
        for (String key : vector2.keySet()) {
            norm2 += Math.pow(vector2.get(key), 2);
        }
        if (norm1 == 0 || norm2 == 0) return 0.0;
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    // 计算文章内容相似度
    private double computeContentSimilarity(Article article1, Article article2) {
        String text1 = article1.getTitle() + " " + article1.getDescription() + " " + article1.getContent();
        String text2 = article2.getTitle() + " " + article2.getDescription() + " " + article2.getContent();

        List<String> allTexts = articleMapper.selectList(null).stream()
                .map(a -> a.getTitle() + " " + a.getDescription() + " " + a.getContent())
                .collect(Collectors.toList());

        Map<String, Double> tfidf1 = computeTFIDF(text1, allTexts);
        Map<String, Double> tfidf2 = computeTFIDF(text2, allTexts);

        return computeCosineSimilarity(tfidf1, tfidf2);
    }

    // 构建用户-文章评分矩阵
    private Map<String, Map<String, Integer>> buildUserArticleMatrix() {
        List<UserBehavior> behaviors = behaviorMapper.selectList(null);
        Map<String, Map<String, Integer>> matrix = new HashMap<>();

        for (UserBehavior behavior : behaviors) {
            matrix.computeIfAbsent(behavior.getUserId(), k -> new HashMap<>())
                    .merge(behavior.getArticleId(), ACTION_SCORES.get(behavior.getBehaviorType()), Integer::sum);
        }
        return matrix;
    }

    // 用户相似度
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

    // 推荐文章
    public List<Article> recommendArticles(String currentArticleId, String userId, int topN) {
        Article currentArticle = articleMapper.selectById(currentArticleId);
        if (currentArticle == null) {
            throw new IllegalArgumentException("文章不存在");
        }

        List<Article> allArticles = articleMapper.selectList(null);
        Map<String, Double> scores = new HashMap<>();

        if (userId == null) {
            // 未登录用户：仅基于内容相关性
            for (Article article : allArticles) {
                if (!article.getId().equals(currentArticleId)) {
                    double similarity = computeContentSimilarity(currentArticle, article);
                    scores.put(article.getId(), similarity);
                }
            }
        } else {
            // 已登录用户：内容相关性 + 行为推荐
            Map<String, Map<String, Integer>> matrix = buildUserArticleMatrix();
            Map<String, Integer> targetUserRatings = matrix.getOrDefault(userId, new HashMap<>());

            // 1. 内容相关性得分
            for (Article article : allArticles) {
                if (!article.getId().equals(currentArticleId)) {
                    double similarity = computeContentSimilarity(currentArticle, article);
                    scores.put(article.getId(), similarity * 0.5); // 内容权重 0.5
                }
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
                    if (!targetUserRatings.containsKey(articleId)) {
                        behaviorScores.merge(articleId, similarity * similarUserRatings.get(articleId), Double::sum);
                    }
                }
            }

            // 合并行为得分
            for (String articleId : behaviorScores.keySet()) {
                scores.merge(articleId, behaviorScores.get(articleId) * 0.5, Double::sum);
            }
        }

        // 返回 topN 篇文章
        return scores.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(topN)
                .map(entry -> articleMapper.selectById(entry.getKey()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
