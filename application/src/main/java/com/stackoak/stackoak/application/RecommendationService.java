package com.stackoak.stackoak.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.application.service.user.IUserBehaviorService;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.user.UserBehavior;
import com.stackoak.stackoak.repository.user.UserBehaviorMapper;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    @Lazy
    private IUserBehaviorService userBehaviorService;
    @Autowired
    @Lazy
    private IArticleService articleService;

    // 定义行为类型与分数的映射
    private static final Map<String, Float> BEHAVIOR_SCORES = new HashMap<>();

    static {
        BEHAVIOR_SCORES.put("LIKE", 3.0f);      // 点赞
        BEHAVIOR_SCORES.put("COLLECT", 4.0f);   // 收藏
        BEHAVIOR_SCORES.put("FOLLOW", 5.0f);    // 关注
        BEHAVIOR_SCORES.put("COMMENT", 5.0f);   // 评论
        BEHAVIOR_SCORES.put("VIEW", 2.0f);      // 阅读
    }

    // 根据行为类型计算得分
    private float calculateScore(UserBehavior behavior) {
        return BEHAVIOR_SCORES.getOrDefault(behavior.getBehaviorType().toUpperCase(), 0.0f);
    }

    // 初始化Mahout数据模型
    private DataModel buildDataModel() {
        List<UserBehavior> behaviors = userBehaviorService.list();
        FastByIDMap<PreferenceArray> userData = new FastByIDMap<>();

        // 按用户分组并计算每个用户对文章的总得分
        Map<String, List<UserBehavior>> userBehaviors = behaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getUserId));

        for (Map.Entry<String, List<UserBehavior>> entry : userBehaviors.entrySet()) {
            String userId = entry.getKey();
            List<UserBehavior> userActions = entry.getValue();

            // 对同一篇文章的多次行为取最高分（或累加，视需求而定）
            Map<String, Float> articleScores = new HashMap<>();
            for (UserBehavior behavior : userActions) {
                String articleId = behavior.getArticleId();
                float score = calculateScore(behavior);
                articleScores.merge(articleId, score, Math::max); // 取最高分
                // 如果需要累加，可以改为：articleScores.merge(articleId, score, Float::sum);
            }

            GenericUserPreferenceArray prefs = new GenericUserPreferenceArray(articleScores.size());
            prefs.setUserID(0, Long.parseLong(userId)); // Mahout需要long类型ID

            int index = 0;
            for (Map.Entry<String, Float> scoreEntry : articleScores.entrySet()) {
                prefs.setItemID(index, Long.parseLong(scoreEntry.getKey()));
                prefs.setValue(index, scoreEntry.getValue());
                index++;
            }
            userData.put(Long.parseLong(userId), prefs);
        }

        return new GenericDataModel(userData);
    }

    // 为登录用户推荐
    public List<Article> getPersonalizedRecommendations(String userId, int page, int size) {
        try {
            DataModel dataModel = buildDataModel();
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, dataModel);
            UserBasedRecommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);

            // 获取推荐结果
            long userIdLong = Long.parseLong(userId);
            List<RecommendedItem> recommendations = recommender.recommend(userIdLong, page * size + size);
            LambdaQueryWrapper<UserBehavior> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserBehavior::getUserId, userId);
            Set<String> interactedArticles = userBehaviorService.list(wrapper)
                    .stream()
                    .map(UserBehavior::getArticleId)
                    .collect(Collectors.toSet());

            // 过滤已交互文章并随机化
            List<Article> result = recommendations.stream()
                    .filter(item -> !interactedArticles.contains(String.valueOf(item.getItemID())))
                    .map(item -> articleService.getById(String.valueOf(item.getItemID())))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Collections.shuffle(result); // 随机化
            int start = page * size;
            int end = Math.min(start + size, result.size());
            return (start >= result.size()) ? Collections.emptyList() : result.subList(start, end);

        } catch (TasteException e) {
            throw new RuntimeException("Recommendation failed", e);
        }
    }

    // 为未登录用户推荐
    public List<Article> getDefaultRecommendations(int page, int size) {
        List<Article> allArticles = articleService.list();
        Collections.shuffle(allArticles); // 随机排序
        int start = page * size;
        int end = Math.min(start + size, allArticles.size());
        return (start >= allArticles.size()) ?
                Collections.emptyList() :
                allArticles.subList(start, end);
    }
}
