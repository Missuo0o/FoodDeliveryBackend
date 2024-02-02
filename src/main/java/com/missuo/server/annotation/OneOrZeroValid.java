package com.missuo.server.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.missuo.server.config.OneOrZeroValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = OneOrZeroValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface OneOrZeroValid {
  String message() default "The value must be either 0 or 1";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
