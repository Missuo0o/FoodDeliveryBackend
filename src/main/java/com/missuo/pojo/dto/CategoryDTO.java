package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class CategoryDTO implements Serializable {

  private Long id;

  private Integer type;

  private String name;

  private Integer sort;
}
