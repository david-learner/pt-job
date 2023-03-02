package com.david.ptjob.item.ui;

import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.service.dto.GettingItemResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemRestControllerTest {

    @Value(value = "${local.server.port}")
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeAll
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @DisplayName("특정 과일 가격 정보를 제공한다")
    @ParameterizedTest
    @ValueSource(strings = {"배", "토마토", "사과", "바나나"})
    void serve_specific_fruit_price(String fruitName) {
        Category fruitCategory = Category.FRUIT;
        String url = baseUrl + "/api/items";
        URI urlWithQueryParams = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("category", fruitCategory.name())
                .queryParam("name", fruitName)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
        RequestEntity request = new RequestEntity(HttpMethod.GET, urlWithQueryParams);

        ResponseEntity<GettingItemResponse> response = restTemplate.exchange(request, GettingItemResponse.class);

        GettingItemResponse item = response.getBody();
        Assertions.assertThat(item.getCategory()).isEqualTo(fruitCategory);
        Assertions.assertThat(item.getName()).isEqualTo(fruitName);
        Assertions.assertThat(item.getPrice()).isNotNull();
    }

    @DisplayName("특정 채소 가격 정보를 제공한다")
    @ParameterizedTest
    @ValueSource(strings = {"치커리", "토마토", "깻잎", "상추"})
    void serve_specific_vegetable_price(String vegetableName) {
        Category vegetableCategory = Category.VEGETABLE;
        String url = baseUrl + "/api/items";
        URI urlWithQueryParams = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("category", vegetableCategory.name())
                .queryParam("name", vegetableName)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
        RequestEntity request = new RequestEntity(HttpMethod.GET, urlWithQueryParams);

        ResponseEntity<GettingItemResponse> response = restTemplate.exchange(request, GettingItemResponse.class);

        GettingItemResponse item = response.getBody();
        Assertions.assertThat(item.getCategory()).isEqualTo(vegetableCategory);
        Assertions.assertThat(item.getName()).isEqualTo(vegetableName);
        Assertions.assertThat(item.getPrice()).isNotNull();
    }

}