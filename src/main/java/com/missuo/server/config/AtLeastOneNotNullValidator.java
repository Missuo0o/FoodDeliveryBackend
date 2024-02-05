package com.missuo.server.config;

import com.missuo.common.constant.MessageConstant;
import com.missuo.common.exception.IllegalException;
import com.missuo.pojo.dto.ShoppingCartDTO;
import com.missuo.server.annotation.AtLeastOneNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {

  @Override
  public void initialize(AtLeastOneNotNull constraintAnnotation) {}

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (!(value instanceof ShoppingCartDTO shoppingCartDTO)) {
      throw new IllegalException(MessageConstant.ILLEGAL_OPERATION);
    }
    return shoppingCartDTO.getDishId() != null || shoppingCartDTO.getSetmealId() != null;
  }
}
