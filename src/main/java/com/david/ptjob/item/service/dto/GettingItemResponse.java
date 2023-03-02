package com.david.ptjob.item.service.dto;

import com.david.ptjob.item.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class GettingItemResponse {

    private Category category;
    private String name;
    private Integer price;
    private LocalDateTime createdAt;
}
