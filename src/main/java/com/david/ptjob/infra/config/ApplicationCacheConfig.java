package com.david.ptjob.infra.config;

import com.david.ptjob.infra.item.ItemCacheEvictionProcessor;
import com.david.ptjob.infra.item.ItemCacheKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;
import java.util.Arrays;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
public class ApplicationCacheConfig {

    private static final long THIRTY_SECONDS = Timer.ONE_SECOND * 30;
    private static final String ITEM_CACHE_NAME = "item";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache(ITEM_CACHE_NAME)));
        return cacheManager;
    }

    @Bean
    public KeyGenerator itemCacheKeyGenerator() {
        return new ItemCacheKeyGenerator();
    }

    @Bean
    public ItemCacheEvictionProcessor itemCacheEvictionProcessor() {
        return new ItemCacheEvictionProcessor();
    }

    @Scheduled(fixedRate = THIRTY_SECONDS)
    public void evictExpiredCache() {
        ItemCacheEvictionProcessor.evictExpiredCache(cacheManager().getCache(ITEM_CACHE_NAME));
    }
}
