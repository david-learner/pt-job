package com.david.ptjob.item.service.dto;

import com.david.ptjob.item.domain.Category;
import com.david.ptjob.item.validation.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GettingItemRequest {

    @CategoryType(anyOf = {Category.FRUIT, Category.VEGETABLE})
    private Category category;

    @NotBlank(message = "청과물 이름을 입력해야 합니다.")
    private String name;
}
