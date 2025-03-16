package com.stackoak.stackoak.application.actors.limit;

import com.stackoak.stackoak.application.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * 全局限流
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final int limit = 100; // 每秒最大请求数
    private final int duration = 1; // 限流时间间隔（秒）

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = request.getRemoteAddr(); // 获取客户端IP
        String key = "RateLimit:Global:" + ip; // 生成Redis Key
        Long count = stringRedisTemplate.opsForValue().increment(key, 1); // 请求计数+1
        if (count == 1) {
            stringRedisTemplate.expire(key, duration, TimeUnit.SECONDS); // 设置Key的过期时间
        }
        if (count > limit) {
            throw new BizException("请求过于频繁，请稍后再试");
        }
        return true;
    }
}
