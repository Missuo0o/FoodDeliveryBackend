package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrdersRejectionDTO implements Serializable {

  private Long id;

  private String rejectionReason;
}
