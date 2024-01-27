package com.missuo.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrdersSubmitDTO implements Serializable {
  private Long addressBookId;

  private int payMethod;

  private String remark;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime estimatedDeliveryTime;

  // Delivery status 1Send immediately 0Select a specific time
  private Integer deliveryStatus;

  private Integer tablewareNumber;

  // Tableware quantity status 1 Provide according to meal size 0 Select specific quantity
  private Integer tablewareStatus;

  private Integer packAmount;

  private BigDecimal amount;
}
