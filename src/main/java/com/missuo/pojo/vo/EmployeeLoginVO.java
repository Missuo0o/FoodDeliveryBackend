package com.missuo.pojo.vo;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Employee Login Returned Data Format")
public class EmployeeLoginVO implements Serializable {

  private Long id;

  private String userName;

  private String name;

  private String token;
}
