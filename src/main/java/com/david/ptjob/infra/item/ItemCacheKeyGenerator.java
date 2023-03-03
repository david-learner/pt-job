package com.david.ptjob.infra.item;

import com.david.ptjob.item.service.dto.GettingItemRequest;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ItemCacheKeyGenerator implements KeyGenerator {

    private static final String SEPARATOR = "_";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        GettingItemRequest item = (GettingItemRequest) params[0];
        String itemCacheKey = item.getCategory().name() + SEPARATOR + item.getName();
        return itemCacheKey;
    }
}
