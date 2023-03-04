package com.david.ptjob.item.validation;

import com.david.ptjob.item.domain.ItemNameCache;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ItemNameValidator implements ConstraintValidator<ItemName, String> {

    private final ItemNameCache itemNameCache;

    @Override
    public void initialize(ItemName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return itemNameCache.isPresent(value);
    }
}
