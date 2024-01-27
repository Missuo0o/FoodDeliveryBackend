package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class SetmealPageQueryDTO implements Serializable {

  private int page;

  private int pageSize;

  private String name;

  private Integer categoryId;

  private Integer status;
}
