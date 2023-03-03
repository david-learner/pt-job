package com.david.ptjob.infra.item;

import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.service.dto.GettingItemRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemCacheKeyGeneratorTest {

    @Test
    @DisplayName("카테고리_이름 포맷을 가지는 아이템 캐시 키를 생성한다")
    void generate_item_cache_key_using_underscore_separator() {
        GettingItemRequest item = new GettingItemRequest(Category.FRUIT, "토마토");
        String fruitTomatoCacheKey = item.getCategory().name() + "_" + item.getName();
        ItemCacheKeyGenerator generator = new ItemCacheKeyGenerator();

        String generatedCacheKey = (String) generator.generate(null, null, item);

        Assertions.assertThat(generatedCacheKey).isEqualTo(fruitTomatoCacheKey);
    }
}