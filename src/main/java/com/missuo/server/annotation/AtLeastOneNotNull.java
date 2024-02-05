package com.missuo.server.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.missuo.server.config.AtLeastOneNotNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AtLeastOneNotNullValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface AtLeastOneNotNull {
  String message() default "At least one of the fields must not be null";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
