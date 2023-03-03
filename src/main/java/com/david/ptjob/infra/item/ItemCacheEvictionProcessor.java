package com.david.ptjob.infra.item;

import com.david.ptjob.item.service.dto.GettingItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
@Slf4j
public class ItemCacheEvictionProcessor {

    private static long baseCacheExpiredTime;

    @Value("${item.cache-expiration-time}")
    public void setBaseCacheExpiredTime(long baseCacheExpiredTime) {
        ItemCacheEvictionProcessor.baseCacheExpiredTime = baseCacheExpiredTime;
    }

    public static void evictExpiredCache(Cache cache) {
        log.debug("Item Caches Eviction Started every {} milliseconds", baseCacheExpiredTime);
        // 기준시간 = 현재시간 - 캐시유지시간
        LocalDateTime baseTime = LocalDateTime.now().minus(Duration.ofMillis(baseCacheExpiredTime));
        ConcurrentMapCache concurrentMapCache = (ConcurrentMapCache) cache;
        ConcurrentMap<Object, Object> itemCaches = concurrentMapCache.getNativeCache();
        Set<Object> keys = itemCaches.keySet();
        for (Object key : keys) {
            GettingItemResponse gettingItemResponse = (GettingItemResponse) itemCaches.get(key);
            boolean isExpired = gettingItemResponse.getCreatedAt().isBefore(baseTime);
            if (isExpired) {
                log.debug("Evicted Cache: '{}'", key);
                itemCaches.remove(key);
            }
        }
    }
}
