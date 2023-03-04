package com.david.ptjob.item.service.dto;

import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.domain.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GettingItemResponse {

    private Category category;
    private String name;
    private Integer price;
    private LocalDateTime createdAt;
    private Boolean isExpired;
    private String message;

    public GettingItemResponse(Category category,
                               String name,
                               Integer price,
                               LocalDateTime createdAt,
                               Boolean isExpired) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
        this.isExpired = isExpired;
        generateExpirationMessage();
    }

    public static GettingItemResponse from(Item item) {
        return GettingItemResponse.of(
                item.getCategory(),
                item.getName(),
                item.getPrice(),
                item.getCreatedAt(),
                item.isExpired()
        );
    }

    public static GettingItemResponse of(Category category,
                                         String name,
                                         Integer price,
                                         LocalDateTime createdAt,
                                         Boolean isExpired) {
        return new GettingItemResponse(category, name, price, createdAt, isExpired);
    }

    private void generateExpirationMessage() {
        if (isExpired) {
            message = String.format("최신 가격 정보가 아닙니다. 참고해 주세요.");
        }
    }
}
