package com.missuo.pojo.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import lombok.Data;

@Data
@ApiModel(description = "Data model passed when employees log in")
public class EmployeeLoginDTO implements Serializable {

  private String username;

  private String password;
}
