package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class OrdersPaymentDTO implements Serializable {
  @NotBlank private String orderNumber;

  @NotNull
  @Range(max = 2)
  private Integer payMethod;
}
