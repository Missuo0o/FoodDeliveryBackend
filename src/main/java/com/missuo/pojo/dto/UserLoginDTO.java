package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserLoginDTO implements Serializable {

  @NotBlank
  @Size(min = 32, max = 32)
  private String code;
}
