package com.stackoka.stackoka.application.controller;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/cache")
public class CacheController {

    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @DeleteMapping("/{cacheName}")
    //@PreAuthorize("hasRole('ADMIN')")
    public String clearCache(@PathVariable String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
            return "Cache cleared for: " + cacheName;
        } else {
            return "Cache not found: " + cacheName;
        }
    }

    @GetMapping("clearall")
    //@PreAuthorize("hasRole('ADMIN')")
    public String clearAllCaches() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        }
        return "All caches cleared.";
    }
}
