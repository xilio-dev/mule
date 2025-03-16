package com.stackoak.stackoak.application.actors.limit;

import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RateLimitAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Before("@annotation(rateLimit)")
    public void beforeMethod(JoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String ip = StpKit.USER.getLoginIdAsString(); // todo 替换为实际获取IP的代码
        String key = "RateLimit:TokenBucket:" + ip + ":" + joinPoint.getSignature().toShortString(); // 生成Redis Key

        long now = System.currentTimeMillis();
        long interval = 1000; // 时间间隔（毫秒）
        long tokens = rateLimit.limit(); // 令牌数量

        // 获取当前令牌桶信息
        String bucket = stringRedisTemplate.opsForValue().get(key);
        long lastRefillTime = bucket == null ? now : Long.parseLong(bucket.split(":")[0]);
        long availableTokens = bucket == null ? tokens : Long.parseLong(bucket.split(":")[1]);

        // 计算新的令牌数量
        long newTokens = ((now - lastRefillTime) * tokens) / interval;
        availableTokens = Math.min(availableTokens + newTokens, tokens);

        // 检查令牌数量
        if (availableTokens < 1) {
            throw new BizException("请求过于频繁，请稍后再试");
        }

        // 更新令牌桶信息
        stringRedisTemplate.opsForValue().set(key, now + ":" + (availableTokens - 1), interval, TimeUnit.MILLISECONDS);
    }
}
