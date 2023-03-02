package com.david.ptjob.item.service;

import com.david.ptjob.infra.item.ItemClient;
import com.david.ptjob.infra.item.ItemClients;
import com.david.ptjob.item.domain.Item;
import com.david.ptjob.item.service.dto.GettingItemRequest;
import com.david.ptjob.item.service.dto.GettingItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemClients itemClients;

    public GettingItemResponse findItem(GettingItemRequest request) {
        // todo 캐시 확인
        ItemClient itemClient = itemClients.findClient(request.getCategory());
        Item item = itemClient.findItemByName(request.getName());
        // todo DB 확인
        return GettingItemResponse.of(item.getCategory(), item.getName(), item.getPrice(), item.getCreatedAt());
    }

}
