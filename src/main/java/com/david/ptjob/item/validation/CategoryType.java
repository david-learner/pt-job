package com.david.ptjob.item.validation;


import com.david.ptjob.item.domain.Category;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CategoryTypeValidator.class)
public @interface CategoryType {
    Category[] anyOf();

    String message() default "청과물 분류를 지정해야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
