package com.david.ptjob.item.service;

import com.david.ptjob.infra.item.ItemClient;
import com.david.ptjob.infra.item.ItemClients;
import com.david.ptjob.item.domain.Item;
import com.david.ptjob.item.service.dto.GettingItemRequest;
import com.david.ptjob.item.service.dto.GettingItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemClients itemClients;

    @Cacheable(value = "item", keyGenerator = "itemCacheKeyGenerator")
    public GettingItemResponse findItem(GettingItemRequest request) {
        ItemClient itemClient = itemClients.findClient(request.getCategory());
        Item item = itemClient.findItemByName(request.getName());
        // todo DB 확인
        return GettingItemResponse.of(item.getCategory(), item.getName(), item.getPrice(), item.getCreatedAt());
    }
}
