package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

@Data
public class OrdersConfirmDTO implements Serializable {

  @NotNull private Long id;

  // Order status 1 Pending payment 2 Pending order 3 Order received 4 Delivery 5 Completed 6
  // Canceled 7 Refund

  private Integer status;
}
