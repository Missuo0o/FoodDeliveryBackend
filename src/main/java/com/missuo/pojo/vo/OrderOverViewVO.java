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
public class OrderOverViewVO implements Serializable {

  private Integer waitingOrders;

  private Integer deliveredOrders;

  private Integer completedOrders;

  private Integer cancelledOrders;

  private Integer allOrders;
}
