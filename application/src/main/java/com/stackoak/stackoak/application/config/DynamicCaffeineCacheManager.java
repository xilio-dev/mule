package com.stackoak.stackoak.application.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

//@EnableCaching // 开启缓存支持
//@Component
//public class DynamicCaffeineCacheManager implements org.springframework.cache.CacheManager {
//
//    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<>();
//
//    private final Caffeine<Object, Object> caffeineBuilder;
//
//    public DynamicCaffeineCacheManager() {
//        this.caffeineBuilder = Caffeine.newBuilder()
//                .expireAfterWrite(10, java.util.concurrent.TimeUnit.MINUTES) // 缓存过期时间：10分钟
//                .maximumSize(500); // 最大缓存条目数：500
//    }
//
//    @Override
//    public Cache getCache(String name) {
//        return cacheMap.computeIfAbsent(name, this::createCache);
//    }
//
//    @Override
//    public Collection<String> getCacheNames() {
//        return Collections.unmodifiableSet(cacheMap.keySet());
//    }
//
//    private Cache createCache(String name) {
//        CaffeineCache cache = new CaffeineCache(name, caffeineBuilder.build());
//        return cache;
//    }
//
//
//    @Bean
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager("verificationCodes");
//        cacheManager.setCaffeine(caffeineCacheBuilder());
//        return cacheManager;
//    }
//
//    private Caffeine<Object, Object> caffeineCacheBuilder() {
//        return Caffeine.newBuilder()
//                .expireAfterWrite(5, TimeUnit.MINUTES) // 验证码过期时间：5分钟
//                .maximumSize(1000); // 最大缓存条目数：1000
//    }
//}


