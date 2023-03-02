package com.david.ptjob.item.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
public class Item {

    private final LocalDateTime createdAt;
    private final Category category;
    private final String name;
    private final Integer price;
}
