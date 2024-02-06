package com.missuo.pojo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {
  private static final long serialVersionUID = 1;

  private Long id;

  private String name;

  private Long orderId;

  private Long dishId;

  private Long setmealId;

  private String dishFlavor;

  private Integer number;

  private BigDecimal amount;

  private String image;
}
