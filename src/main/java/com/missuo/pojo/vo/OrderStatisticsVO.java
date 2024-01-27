package com.missuo.pojo.vo;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrderStatisticsVO implements Serializable {
  private Integer toBeConfirmed;

  private Integer confirmed;

  private Integer deliveryInProgress;
}
