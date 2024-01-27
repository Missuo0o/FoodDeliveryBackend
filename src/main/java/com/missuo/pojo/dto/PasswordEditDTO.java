package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class PasswordEditDTO implements Serializable {

  private Long empId;

  private String oldPassword;

  private String newPassword;
}
