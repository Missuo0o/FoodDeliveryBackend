package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class EmployeePageQueryDTO implements Serializable {

  private String name;

  private int page;

  private int pageSize;
}
