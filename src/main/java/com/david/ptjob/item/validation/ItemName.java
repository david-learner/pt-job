package com.david.ptjob.item.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ItemNameValidator.class)
public @interface ItemName {

    String message() default "가격을 제공하지 않는 청과물입니다.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
