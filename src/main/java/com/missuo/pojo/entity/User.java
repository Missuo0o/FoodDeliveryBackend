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
public class User implements Serializable {
  private static final long serialVersionUID = 1;

  private Long id;

  private String openid;

  private String name;

  private String phone;

  // 0 Female 1 Male
  private Integer sex;

  private String idNumber;

  private String avatar;

  private LocalDateTime createTime;
}
