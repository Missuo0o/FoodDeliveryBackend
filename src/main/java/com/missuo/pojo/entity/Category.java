package com.missuo.pojo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  // 1 Category of dishes 2 Category of combos
  private Integer type;

  private String name;

  private Integer sort;

  // 0 identifies disabled 1 indicates enabled
  private Integer status;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  private Long createUser;

  private Long updateUser;
}
