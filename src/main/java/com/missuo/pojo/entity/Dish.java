package com.missuo.pojo.entity;

import java.io.Serial;
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
public class Dish implements Serializable {
  @Serial private static final long serialVersionUID = 1;

  private Long id;

  private String name;

  private Long categoryId;

  private BigDecimal price;

  private String image;

  private String description;

  // 0 Stop selling 1 Start selling
  private Integer status;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  private Long createUser;

  private Long updateUser;
}
