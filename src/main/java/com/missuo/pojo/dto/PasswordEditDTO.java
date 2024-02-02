package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class PasswordEditDTO implements Serializable {

  private Long empId;

  @NotNull
  @Size(min = 6, max = 20)
  private String oldPassword;

  @NotNull
  @Size(min = 6, max = 20)
  private String newPassword;
}
