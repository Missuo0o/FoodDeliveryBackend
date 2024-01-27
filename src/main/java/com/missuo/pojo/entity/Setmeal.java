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
public class Setmeal implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long categoryId;

  private String name;

  private BigDecimal price;

  // 0 Disable 1 Enable
  private Integer status;

  private String description;

  private String image;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  private Long createUser;

  private Long updateUser;
}
