package com.stackoak.stackoak.application.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching // 开启缓存支持
public class CacheConfig {

    @Bean
    public DynamicCaffeineCacheManager cacheManager() {
        return new DynamicCaffeineCacheManager();
    }

    public static class DynamicCaffeineCacheManager implements org.springframework.cache.CacheManager {

        private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();

        private final Caffeine<Object, Object> caffeineBuilder;

        public DynamicCaffeineCacheManager() {
            // 默认缓存配置
            this.caffeineBuilder = Caffeine.newBuilder()
                    .expireAfterWrite(10, TimeUnit.MINUTES) // 默认缓存过期时间：10分钟
                    .maximumSize(500); // 默认最大缓存条目数：500
        }

        @Override
        public Cache getCache(String name) {
            return cacheMap.computeIfAbsent(name, this::createCache);
        }

        @Override
        public Collection<String> getCacheNames() {
            return Collections.unmodifiableSet(cacheMap.keySet());
        }

        private Cache createCache(String name) {
            // 根据缓存名称动态调整配置
            Caffeine<Object, Object> builder = caffeineBuilder;
            if ("verificationCodes".equals(name)) {
                // 验证码专用配置
                builder = Caffeine.newBuilder()
                        .expireAfterWrite(5, TimeUnit.MINUTES) // 验证码过期时间：5分钟
                        .maximumSize(1000); // 验证码最大缓存条目数：1000
            }
            return new CaffeineCache(name, builder.build());
        }
    }
}
