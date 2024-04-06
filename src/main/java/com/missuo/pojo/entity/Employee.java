package com.missuo.pojo.entity;

import java.io.Serial;
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
public class Employee implements Serializable {
  @Serial private static final long serialVersionUID = 1;

  private Long id;

  private String username;

  private String name;

  private String password;

  private String phone;

  private Integer sex;

  private String idNumber;

  private Integer status;

  //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  private Long createUser;

  private Long updateUser;
}
