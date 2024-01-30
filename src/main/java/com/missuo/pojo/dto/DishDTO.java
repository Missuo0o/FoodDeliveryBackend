package com.missuo.pojo.dto;

import com.missuo.pojo.entity.DishFlavor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class DishDTO implements Serializable {

  private Long id;

  private String name;

  private Long categoryId;

  private BigDecimal price;

  private String image;

  private String description;

  private Integer status;

  private List<DishFlavor> flavors;
}
