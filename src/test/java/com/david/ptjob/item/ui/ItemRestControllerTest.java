package com.david.ptjob.item.ui;

import com.david.ptjob.common.ui.dto.ApiResponse;
import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.service.dto.GettingItemResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ItemRestControllerTest {

    private static ParameterizedTypeReference<ApiResponse<GettingItemResponse>> referencedApiResponseType
            = getApiResponseParameterizedTypeReference(GettingItemResponse.class);

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
        URI urlWithQueryParams = generateFindItemUrl(fruitCategory.name(), fruitName);
        RequestEntity request = new RequestEntity(HttpMethod.GET, urlWithQueryParams);

        ResponseEntity<ApiResponse<GettingItemResponse>> response = restTemplate.exchange(request, referencedApiResponseType);

        ApiResponse<GettingItemResponse> wrappedItem = response.getBody();
        GettingItemResponse item = wrappedItem.getBody();
        Assertions.assertThat(item.getCategory()).isEqualTo(fruitCategory);
        Assertions.assertThat(item.getName()).isEqualTo(fruitName);
        Assertions.assertThat(item.getPrice()).isNotNull();
    }

    @DisplayName("특정 채소 가격 정보를 제공한다")
    @ParameterizedTest
    @ValueSource(strings = {"치커리", "토마토", "깻잎", "상추"})
    void serve_specific_vegetable_price(String vegetableName) {
        Category vegetableCategory = Category.VEGETABLE;
        URI urlWithQueryParams = generateFindItemUrl(vegetableCategory.name(), vegetableName);
        RequestEntity request = new RequestEntity(HttpMethod.GET, urlWithQueryParams);

        ResponseEntity<ApiResponse<GettingItemResponse>> response = restTemplate.exchange(request, referencedApiResponseType);

        ApiResponse<GettingItemResponse> wrappedItem = response.getBody();
        GettingItemResponse item = wrappedItem.getBody();
        Assertions.assertThat(item.getCategory()).isEqualTo(vegetableCategory);
        Assertions.assertThat(item.getName()).isEqualTo(vegetableName);
        Assertions.assertThat(item.getPrice()).isNotNull();
    }

    @DisplayName("청과물 가격 조회시, 청과물의 이름이 비어있으면 400 오류를 반환한다")
    @Test
    void when_name_is_blank_return_400_error() {
        URI urlWithQueryParams = generateFindItemUrl(Category.FRUIT.name(), "");
        RequestEntity request = new RequestEntity(HttpMethod.GET, urlWithQueryParams);

        ResponseEntity<ApiResponse<GettingItemResponse>> response = restTemplate.exchange(request, referencedApiResponseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("청과물 이름을 입력해야 합니다.");
        Assertions.assertThat(response.getBody().getBody()).isNull();
    }

    @DisplayName("청과물 가격 조회시, 청과물 분류가 비어있으면 400 오류를 반환한다")
    @Test
    void when_category_is_blank_return_400_error() {
        URI urlWithQueryParams = generateFindItemUrl("", "배");
        RequestEntity request = new RequestEntity(HttpMethod.GET, urlWithQueryParams);

        ResponseEntity<ApiResponse<GettingItemResponse>> response = restTemplate.exchange(request, referencedApiResponseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody().getMessage()).isEqualTo("청과물 분류를 지정해야 합니다.");
        Assertions.assertThat(response.getBody().getBody()).isNull();
    }

    /**
     * 아이템 조회용 URL를 생성한다
     * /api/item?category=[category]&name=[name]
     */
    private URI generateFindItemUrl(String categoryName, String name) {
        String url = baseUrl + "/api/items";
        URI urlWithQueryParams = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("category", categoryName)
                .queryParam("name", name)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
        return urlWithQueryParams;
    }

    /**
     * ApiResponse의 제네릭 타입을 추론할 수 있도록 돕는 ParameterizedTypeReference를 반환한다
     */
    private static <T> ParameterizedTypeReference<ApiResponse<T>> getApiResponseParameterizedTypeReference(Class<T> wrappedClass) {
        ResolvableType apiResponseTypeHaveGettingItemResponseAsGeneric = ResolvableType.forClassWithGenerics(ApiResponse.class, wrappedClass);
        ParameterizedTypeReference<ApiResponse<T>> referencedType = ParameterizedTypeReference.forType(apiResponseTypeHaveGettingItemResponseAsGeneric.getType());
        return referencedType;
    }
}