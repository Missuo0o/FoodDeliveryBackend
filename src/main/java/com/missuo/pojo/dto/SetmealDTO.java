package com.missuo.pojo.dto;

import com.missuo.pojo.entity.SetmealDish;
import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class SetmealDTO implements Serializable {

  private Long id;

  @NotNull
  @Min(value = 1)
  private Long categoryId;

  @NotNull
  @Size(min = 1, max = 20)
  private String name;

  @NotNull
  @DecimalMin(value = "1.00")
  @DecimalMax(value = "99999.99")
  private BigDecimal price;

  @OneOrZeroValid private Integer status;

  private String description;

  @NotNull
  @Size(min = 1, max = 255)
  private String image;

  @NotNull
  @Size(min = 1)
  private List<SetmealDish> setmealDishes;
}
