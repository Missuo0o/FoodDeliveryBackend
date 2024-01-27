package com.missuo.pojo.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDataVO implements Serializable {

  private Double turnover;

  private Integer validOrderCount;

  private Double orderCompletionRate;

  private Double unitPrice;

  private Integer newUsers;
}
