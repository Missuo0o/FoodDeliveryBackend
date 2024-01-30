package com.missuo.pojo.dto;

import com.missuo.pojo.entity.SetmealDish;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class SetmealDTO implements Serializable {

  private Long id;

  private Long categoryId;

  private String name;

  private BigDecimal price;

  private Integer status;

  private String description;

  private String image;

  private List<SetmealDish> setmealDishes;
}
