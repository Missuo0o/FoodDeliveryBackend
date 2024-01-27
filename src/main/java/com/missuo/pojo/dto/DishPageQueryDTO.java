package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class DishPageQueryDTO implements Serializable {

  private int page;

  private int pageSize;

  private String name;

  private Integer categoryId;

  private Integer status;
}
