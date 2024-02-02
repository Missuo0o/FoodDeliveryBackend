package com.missuo.pojo.dto;

import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class EmployeeDTO implements Serializable {

  private Long id;

  @NotNull
  @Size(min = 3, max = 20)
  private String username;

  @NotNull
  @Size(min = 1, max = 12)
  private String name;

  @NotNull
  @Size(min = 10, max = 10)
  private String phone;

  @OneOrZeroValid private Integer sex;

  @NotNull
  @Size(min = 9, max = 9)
  private String idNumber;
}
