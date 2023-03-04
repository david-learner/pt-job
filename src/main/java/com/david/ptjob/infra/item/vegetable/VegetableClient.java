package com.david.ptjob.infra.item.vegetable;

import com.david.ptjob.infra.item.ItemClient;
import com.david.ptjob.infra.item.dto.ItemResponse;
import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.domain.Item;
import com.david.ptjob.item.repository.ItemRepository;
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
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VegetableClient implements ItemClient {

    private static final String EQUAL_SYMBOL = "=";
    private static final String WHITE_SPACE = " ";
    private final RestTemplate restTemplate;
    private final VegetableProperties vegetableProperties;
    private final ItemRepository itemRepository;

    @Retryable(value = {HttpServerErrorException.class}, backoff = @Backoff(2000))
    @Override
    public Item findItemByName(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, getAccessToken());
        HttpEntity findVegetableRequest = new HttpEntity(headers);
        String url = createUrl("/item?name=" + name);

        ResponseEntity<ItemResponse> response = restTemplate.exchange(url, HttpMethod.GET, findVegetableRequest, ItemResponse.class);
        log.debug("채소 정보 응답: '{}'", response.getBody().toString());
        return response.getBody().toItem(Category.VEGETABLE);
    }

    @Recover
    public Item recoverFindingItemByName(String name) {
        return itemRepository.findItemByCategoryAndName(Category.VEGETABLE, name)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("청과물 가격을 제공할 수 없습니다. 잠시 후 다시 시도해 주세요.");
                });
    }

    @Override
    public String getAccessToken() {
        String url = createUrl("/token");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        return extractAccessTokenFromCookie(cookies);
    }

    private String createUrl(String pathAndParameters) {
        return vegetableProperties.getUri() + pathAndParameters;
    }

    private String extractAccessTokenFromCookie(List<String> cookies) {
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
