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
public class OrderReportVO implements Serializable {

  private String dateList;

  private String orderCountList;

  private String validOrderCountList;

  private Integer totalOrderCount;

  private Integer validOrderCount;

  private Double orderCompletionRate;
}
