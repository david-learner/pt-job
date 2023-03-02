package com.david.ptjob.item.validation;

import com.david.ptjob.item.domain.Category;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class CategoryTypeValidator implements ConstraintValidator<CategoryType, Category> {

    private Category[] categories;
    
    @Override
    public void initialize(CategoryType constraintAnnotation) {
        this.categories = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Category value, ConstraintValidatorContext context) {
        return Arrays.asList(categories).contains(value);
    }
}
