package com.david.ptjob.infra.item.vegetable;

import com.david.ptjob.infra.item.ItemClient;
import com.david.ptjob.infra.item.dto.ItemResponse;
import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.domain.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VegetableClient implements ItemClient {

    private static final String EQUAL_SYMBOL = "=";
    private static final String WHITE_SPACE = " ";
    private final RestTemplate restTemplate;
    private final VegetableProperties vegetableProperties;

    @Override
    public Item findItemByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, getAccessToken());
        HttpEntity findVegetableRequest = new HttpEntity(headers);
        String url = vegetableProperties.getUri() + "/item?name=" + name;
        ResponseEntity<ItemResponse> response = restTemplate.exchange(url, HttpMethod.GET, findVegetableRequest, ItemResponse.class);
        log.debug("채소 정보 응답: '{}'", response.getBody().toString());
        return response.getBody().toItem(Category.VEGETABLE);
    }

    @Override
    public String getAccessToken() {
        String url = vegetableProperties.getUri() + "/token";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        String authorizationCookie = cookies.stream()
                .filter(cookie -> cookie.contains(HttpHeaders.AUTHORIZATION))
                .findAny()
                .orElseThrow(IllegalStateException::new);
        authorizationCookie = authorizationCookie.split(WHITE_SPACE)[0];
        String authorizationCookiePrefix = HttpHeaders.AUTHORIZATION + EQUAL_SYMBOL;
        String accessToken = authorizationCookie.substring(authorizationCookiePrefix.length(), authorizationCookie.length() - 1);
        log.debug("채소 토큰 응답: '{}'", accessToken);
        return accessToken;
    }
}
