package com.missuo.pojo.dto;

import com.missuo.pojo.entity.OrderDetail;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class OrdersDTO implements Serializable {

  private Long id;

  private String number;

  // Order status 1 pending payment, 2 awaiting delivery, 3 delivered, 4 completed, 5 canceled
  private Integer status;

  private Long userId;

  private Long addressBookId;

  private LocalDateTime orderTime;

  private LocalDateTime checkoutTime;

  // Payment method 1WeChat, 2Alipay
  private Integer payMethod;

  private BigDecimal amount;

  private String remark;

  private String userName;

  private String phone;

  private String address;

  private String consignee;

  private List<OrderDetail> orderDetails;
}
