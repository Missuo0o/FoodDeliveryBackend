package com.missuo.pojo.vo;

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
public class BusinessDataVO implements Serializable {

  private BigDecimal turnover;

  private Integer validOrderCount;

  private Double orderCompletionRate;

  private BigDecimal unitPrice;

  private Integer newUsers;
}
