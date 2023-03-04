package com.david.ptjob.infra.item.fruit;

import com.david.ptjob.infra.item.ItemClient;
import com.david.ptjob.infra.item.dto.ItemResponse;
import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.domain.Item;
import com.david.ptjob.item.repository.ItemRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
@Slf4j
public class FruitClient implements ItemClient {

    private final RestTemplate restTemplate;
    private final FruitProperties fruitProperties;
    private final ItemRepository itemRepository;

    @Retryable(value = {HttpServerErrorException.class}, backoff = @Backoff(2000))
    @Override
    public Item findItemByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, getAccessToken());
        HttpEntity findFruitRequest = new HttpEntity(headers);
        String url = fruitProperties.getUri() + "/product?name=" + name;

        ResponseEntity<ItemResponse> response = restTemplate.exchange(url, HttpMethod.GET, findFruitRequest, ItemResponse.class);
        log.debug("과일 정보 응답: '{}'", response.getBody().toString());
        Item item = response.getBody().toItem(Category.FRUIT);
        Item savedItem = itemRepository.save(item);
        return savedItem;
    }

    @Recover
    public Item recoverFindingItemByName(String name) {
        Item item = itemRepository.findItemByCategoryAndName(Category.FRUIT, name)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("청과물 가격을 제공할 수 없습니다. 잠시 후 다시 시도해 주세요.");
                });
        item.updateExpirationStatus();
        return item;
    }

    @Override
    public String getAccessToken() {
        String url = fruitProperties.getUri() + "/token";
        AccessToken accessToken = restTemplate.getForObject(url, AccessToken.class);
        log.debug("과일 토큰 응답: '{}'", accessToken.getValue());
        return accessToken.getValue();
    }

    @Getter
    static class AccessToken {
        @JsonProperty("accessToken")
        private String value;
    }
}
