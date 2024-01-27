package com.missuo.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitVO implements Serializable {
  private Long id;

  private String orderNumber;

  private BigDecimal orderAmount;

  private LocalDateTime orderTime;
}
