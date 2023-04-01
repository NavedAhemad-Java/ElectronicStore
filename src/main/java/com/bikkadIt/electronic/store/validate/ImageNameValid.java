package com.bikkadIt.electronic.store.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

// error message
    String message() default "{javax.validation.constraints.NotBlank.message}";

    //respresent group of constraints
    Class<?>[] groups() default { };

    //additional Information about annotation
    Class<? extends Payload>[] payload() default { };//

}
