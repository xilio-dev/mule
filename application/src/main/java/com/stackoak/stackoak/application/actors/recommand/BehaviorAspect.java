package com.stackoak.stackoak.application.actors.recommand;

import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.common.data.user.UserBehavior;
import com.stackoak.stackoak.repository.user.UserBehaviorMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 用户行为日志收集，用于推荐系统使用
 */
@Aspect
@Component
@Lazy
public class BehaviorAspect {

    @Autowired
    private UserBehaviorMapper userBehaviorMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 拦截所有控制器方法，通过注解识别行为
    @Around("@annotation(behaviorLog)")
    public Object logBehavior(ProceedingJoinPoint joinPoint, BehaviorLog behaviorLog) throws Throwable {
        // 执行原方法
        Object result = joinPoint.proceed();

        // 检查用户是否登录
        if (!StpKit.USER.isLogin()) {
            return result;
        }

        // 通过 SpEL 表达式提取 businessId
        String businessId = BusinessIdExtractor.extractBusinessId(joinPoint, behaviorLog.businessId());

        // 获取当前用户
        String userId = StpKit.USER.getLoginIdAsString();

        // 如果是 VIEW 行为，检查是否在 12 小时内已记录
        if (behaviorLog.type() == BehaviorType.ViEW) {
            if (isViewRecordedRecently(userId, businessId)) {
                return result; // 如果已记录，直接返回
            }
        }

        // 记录行为
        UserBehavior behavior = new UserBehavior();
        behavior.setUserId(userId);
        behavior.setBehaviorType(behaviorLog.type().getType());
        behavior.setArticleId(businessId);
        behavior.setBehaviorTime(LocalDateTime.now());
        userBehaviorMapper.insert(behavior);

        // 如果是 VIEW 行为，更新 Redis 记录
        if (behaviorLog.type() == BehaviorType.ViEW) {
            updateViewRecord(userId, businessId);
        }

        return result;
    }

    /**
     * 检查用户是否在 12 小时内已记录过 VIEW 行为
     */
    private boolean isViewRecordedRecently(String userId, String articleId) {
        String key = buildViewKey(userId, articleId);
        return redisTemplate.hasKey(key);
    }

    /**
     * 更新用户的 VIEW 行为记录
     */
    private void updateViewRecord(String userId, String articleId) {
        String key = buildViewKey(userId, articleId);
        //todo 后续优化：动态调整
        redisTemplate.opsForValue().set(key, LocalDateTime.now().toString(), 12, TimeUnit.HOURS);
    }

    /**
     * 构建 Redis Key
     */
    private String buildViewKey(String userId, String articleId) {
        return "user_view:" + userId + ":" + articleId;
    }
}
