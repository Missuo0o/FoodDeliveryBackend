package com.missuo.pojo.entity;

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
public class Orders implements Serializable {

  public static final Integer PENDING_PAYMENT = 1;
  public static final Integer TO_BE_CONFIRMED = 2;
  public static final Integer CONFIRMED = 3;
  public static final Integer DELIVERY_IN_PROGRESS = 4;
  public static final Integer COMPLETED = 5;
  public static final Integer CANCELLED = 6;

  public static final Integer UN_PAID = 0;
  public static final Integer PAID = 1;
  public static final Integer REFUND = 2;

  private static final long serialVersionUID = 1L;

  private Long id;

  private String number;

  // 1 Pending Payment 2 Pending Order 3 Received Order 4 In Dispatch 5 Completed 6 Canceled 7
  // Refunded
  private Integer status;

  private Long userId;

  private Long addressBookId;

  private LocalDateTime orderTime;

  private LocalDateTime checkoutTime;

  // 1 WeChat, 2 Alipay
  private Integer payMethod;

  // 0 Not paid 1 Paid 2 Refunded
  private Integer payStatus;

  private BigDecimal amount;

  private String remark;

  private String userName;

  private String phone;

  private String address;

  private String consignee;

  private String cancelReason;

  private String rejectionReason;

  private LocalDateTime cancelTime;

  private LocalDateTime estimatedDeliveryTime;

  // 1 Delivered immediately 0 Choose a specific time
  private Integer deliveryStatus;

  private LocalDateTime deliveryTime;

  private int packAmount;

  private int tablewareNumber;

  // 1 Provided by meal size 0 Select specific quantity
  private Integer tablewareStatus;
}
