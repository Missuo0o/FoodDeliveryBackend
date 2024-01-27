package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class OrdersCancelDTO implements Serializable {

  private Long id;

  private String cancelReason;
}
