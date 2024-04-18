package com.missuo.pojo.dto;

import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import java.io.Serializable;
import lombok.Data;

@Data
public class EmployeeDTO implements Serializable {

  @NotNull(groups = Update.class)
  private Long id;

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 12)
  private String name;

  @NotBlank
  @Size(min = 10, max = 10)
  private String phone;

  @OneOrZeroValid private Integer sex;

  @NotNull
  @Size(min = 9, max = 9)
  private String idNumber;

  public interface Update extends Default {}
}
