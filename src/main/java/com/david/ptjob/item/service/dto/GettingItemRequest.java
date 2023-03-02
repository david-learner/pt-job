package com.david.ptjob.item.service.dto;

import com.david.ptjob.item.domain.Category;
import lombok.Data;

@Data
public class GettingItemRequest {

    private Category category;
    private String name;
}
