package com.stackoak.stackoak.application.actors.recommand;

import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.service.user.IUserBehaviorService;
import com.stackoak.stackoak.common.data.user.UserBehavior;
import com.stackoak.stackoak.repository.user.UserBehaviorMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 用户行为日志收集，用于推荐系统使用
 */
@Aspect
@Component
@Lazy
public class BehaviorAspect {
   @Autowired
    private UserBehaviorMapper userBehaviorMapper;
    // 拦截所有控制器方法，通过注解识别行为
    @Around("@annotation(behaviorLog)")
    public Object logBehavior(ProceedingJoinPoint joinPoint, BehaviorLog behaviorLog) throws Throwable {
        // 执行原方法
        Object result = joinPoint.proceed();
        if (!StpKit.USER.isLogin()){
            return result;
        }
        // 通过 SpEL 表达式提取 businessId
        String businessId = BusinessIdExtractor.extractBusinessId(joinPoint, behaviorLog.businessId());
        // 获取当前用户
        String userId = StpKit.USER.getLoginIdAsString();
        UserBehavior behavior = new UserBehavior();
        behavior.setUserId(userId);
        behavior.setBehaviorType(behaviorLog.type().name());
        behavior.setArticleId(businessId);
        behavior.setBehaviorTime(LocalDateTime.now());
        System.out.println("==>"+behavior);
        userBehaviorMapper.insert(behavior);
        return result;
    }

}
