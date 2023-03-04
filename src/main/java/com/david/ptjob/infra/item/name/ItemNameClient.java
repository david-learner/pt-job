package com.david.ptjob.infra.item.name;

import com.david.ptjob.infra.item.fruit.FruitClient;
import com.david.ptjob.infra.item.fruit.FruitProperties;
import com.david.ptjob.infra.item.vegetable.VegetableClient;
import com.david.ptjob.infra.item.vegetable.VegetableProperties;
import com.david.ptjob.item.domain.ItemNameCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemNameClient {

    private final FruitClient fruitClient;
    private final FruitProperties fruitProperties;
    private final VegetableClient vegetableClient;
    private final VegetableProperties vegetableProperties;
    private final RestTemplate restTemplate;
    private final ItemNameCache itemNameCache;

    @PostConstruct
    public void findItemNames() {
        itemNameCache.addAll(findFruitNames());
        itemNameCache.addAll(findVegetableNames());
    }

    private List<String> findFruitNames() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, fruitClient.getAccessToken());
        HttpEntity findFruitNamesRequest = new HttpEntity(headers);
        String url = fruitProperties.getUri() + "/product";

        ResponseEntity<ArrayList> response = restTemplate.exchange(url, HttpMethod.GET, findFruitNamesRequest, ArrayList.class);
        return response.getBody();
    }

    private List<String> findVegetableNames() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, vegetableClient.getAccessToken());
        HttpEntity findVegetableNamesRequest = new HttpEntity(headers);
        String url = vegetableProperties.getUri() + "/item";

        ResponseEntity<ArrayList> response = restTemplate.exchange(url, HttpMethod.GET, findVegetableNamesRequest, ArrayList.class);
        return response.getBody();
    }
}
