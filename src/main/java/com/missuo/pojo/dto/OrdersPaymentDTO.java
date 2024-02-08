package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class OrdersPaymentDTO implements Serializable {
  @NotNull
  @Size(min = 1)
  private String orderNumber;

  @NotNull
  @Range(min = 1, max = 2)
  private Integer payMethod;
}
