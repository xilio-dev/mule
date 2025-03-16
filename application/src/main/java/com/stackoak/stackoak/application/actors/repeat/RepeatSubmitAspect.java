package com.stackoak.stackoak.application.actors.repeat;

import com.stackoak.stackoak.application.actors.security.StpKit;
import com.stackoak.stackoak.application.exception.BizException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RepeatSubmitAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Before("@annotation(repeatSubmit)")
    public void beforeMethod(JoinPoint joinPoint, RepeatSubmit repeatSubmit) throws Throwable {
        // 获取当前用户ID
        String userId = StpKit.USER.getLoginIdAsString();

        // 获取请求参数
        Object[] args = joinPoint.getArgs();
        String requestParams = Arrays.toString(args); // 将请求参数转换为字符串

        // 生成固定请求ID（用户ID + 请求参数的哈希值）
        String requestId = userId + ":" + requestParams.hashCode();

        // 生成Redis Key
        String lockKey = "RepeatSubmit:" + requestId;

        // 设置Redis Key，如果已存在则抛出异常
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "", repeatSubmit.expireTime(), TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(result)) {
            throw new BizException("请勿重复提交");
        }
    }
}
