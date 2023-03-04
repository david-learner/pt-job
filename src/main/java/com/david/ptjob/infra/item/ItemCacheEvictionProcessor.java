package com.david.ptjob.infra.item;

import com.david.ptjob.item.domain.ItemExpirationValidator;
import com.david.ptjob.item.service.dto.GettingItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

@Slf4j
public class ItemCacheEvictionProcessor {

    public static void evictExpiredCache(Cache cache) {
        log.debug("Item Caches Eviction Started");
        ConcurrentMapCache concurrentMapCache = (ConcurrentMapCache) cache;
        ConcurrentMap<Object, Object> itemCaches = concurrentMapCache.getNativeCache();
        Set<Object> keys = itemCaches.keySet();

        for (Object key : keys) {
            GettingItemResponse gettingItemResponse = (GettingItemResponse) itemCaches.get(key);
            boolean isExpired = ItemExpirationValidator.isExpiredFrom(LocalDateTime.now(), gettingItemResponse.getCreatedAt());
            if (isExpired) {
                log.debug("Evicted Cache: '{}'", key);
                itemCaches.remove(key);
            }
        }
        log.debug("Item Caches Eviction Finished");
    }
}
