package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrdersPaymentDTO implements Serializable {

  private String orderNumber;

  private Integer payMethod;
}
