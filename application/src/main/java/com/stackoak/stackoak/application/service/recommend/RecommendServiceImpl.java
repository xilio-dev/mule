package com.stackoak.stackoak.application.service.recommend;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import com.stackoak.stackoak.application.service.article.IArticleService;
import com.stackoak.stackoak.application.service.user.IUserBehaviorService;
import com.stackoak.stackoak.application.service.user.IUserService;
import com.stackoak.stackoak.common.data.PageQuery;
import com.stackoak.stackoak.common.data.article.Article;
import com.stackoak.stackoak.common.data.article.ArticleBriefVO;
import com.stackoak.stackoak.common.data.recommend.RecommendByUserQuery;
import com.stackoak.stackoak.common.data.user.User;
import com.stackoak.stackoak.common.data.user.UserBehavior;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendServiceImpl implements IRecommendService {

    @Autowired
    private IUserBehaviorService userBehaviorService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IUserService userService;
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

    /**
     * 个性化推荐用户文章
     *
     * @param userId    用户ID
     * @param pageQuery 分页参数
     * @return 推荐结果
     */
    @Override
    public Page<Article> getPersonalizedArticleRecommendations(String userId, PageQuery pageQuery) {
        try {
            int page = Math.toIntExact(pageQuery.getCurrent());
            int size = Math.toIntExact(pageQuery.getSize());
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
            Page<Article> pageRes = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
            List<Article> articles = result.subList(start, end);
            pageRes.setRecords(articles);
            return pageRes;

        } catch (TasteException e) {
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 用户没有登陆时，提供默认推荐
     *
     * @param pageQuery 分页参数
     * @return 推荐结果
     */
    @Override
    public Page<Article> getDefaultArticleRecommendations(PageQuery pageQuery) {
        //todo 测试
        return articleService.page(Page.of(pageQuery.getCurrent(), pageQuery.getSize()));
//        Collections.shuffle(allArticles); // 随机排序
//        int start = page * size;
//        int end = Math.min(start + size, allArticles.size());
//        return (start >= allArticles.size()) ?
//                Collections.emptyList() :
//                allArticles.subList(start, end);
    }


    public Page<User> recommendAuthors(String userId, PageQuery pageQuery) {
        int page = Math.toIntExact(pageQuery.getCurrent());
        int size = Math.toIntExact(pageQuery.getSize());
        int offset = page * size;
        Page<User> res = Page.of(pageQuery.getCurrent(), pageQuery.getSize());
        if (userId != null && !userId.isEmpty()) {
            List<User> users = recommendForLoggedInUser(userId, offset, size);
            res.setRecords(users);
        } else {
            List<User> users = recommendForGuest(offset, size);
            res.setRecords(users);
        }
        return res;
    }

    private List<User> recommendForLoggedInUser(String userId, int offset, int size) {
        // 获取用户行为
        LambdaQueryWrapper<UserBehavior> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserBehavior::getUserId, userId);
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
        if (authorIds.isEmpty()) {
            return Collections.emptyList();
        }
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
