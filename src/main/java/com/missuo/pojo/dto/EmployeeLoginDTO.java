package com.missuo.pojo.dto;

import io.swagger.annotations.ApiModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
@ApiModel(description = "Data model passed when employees log in")
public class EmployeeLoginDTO implements Serializable {

  @NotNull
  @Size(min = 3, max = 20)
  private String username;

  @NotNull
  @Size(min = 6, max = 20)
  private String password;
}
