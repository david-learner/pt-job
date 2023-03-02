package com.david.ptjob.infra.item.fruit;

import com.david.ptjob.infra.item.ItemClient;
import com.david.ptjob.infra.item.dto.ItemResponse;
import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.domain.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class FruitClient implements ItemClient {

    private final RestTemplate restTemplate;
    private final FruitProperties fruitProperties;

    @Override
    public Item findItemByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, getAccessToken());
        HttpEntity findFruitRequest = new HttpEntity(headers);
        String url = fruitProperties.getUri() + "/product?name=" + name;
        ResponseEntity<ItemResponse> response = restTemplate.exchange(url, HttpMethod.GET, findFruitRequest, ItemResponse.class);
        log.debug("과일 정보 응답: '{}'", response.getBody().toString());

        return response.getBody().toItem(Category.FRUIT);
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
