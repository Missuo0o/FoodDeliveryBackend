package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserLoginDTO implements Serializable {

  @NotNull
  @Size(min = 32, max = 32)
  private String code;
}
