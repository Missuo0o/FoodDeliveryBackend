package com.missuo.server.config;

import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OneOrZeroValidator implements ConstraintValidator<OneOrZeroValid, Integer> {

  @Override
  public void initialize(OneOrZeroValid constraintAnnotation) {}

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    return value == 0 || value == 1;
  }
}
