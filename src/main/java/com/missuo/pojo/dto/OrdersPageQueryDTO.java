package com.missuo.pojo.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class OrdersPageQueryDTO implements Serializable {

  private int page;

  private int pageSize;

  private String number;

  private String phone;

  private Integer status;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime beginTime;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime endTime;

  private Long userId;
}
