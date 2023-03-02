package com.david.ptjob.infra.item.dto;

import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.domain.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemResponse {

    private String name;
    private Integer price;

    public Item toItem(Category category) {
        return Item.of(LocalDateTime.now(), category, name, price);
    }
}
