package com.missuo.pojo.dto;

import com.missuo.pojo.entity.DishFlavor;
import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class DishDTO implements Serializable {

  private Long id;

  @NotNull
  @Size(min = 1, max = 20)
  private String name;

  @NotNull
  @Min(value = 1)
  private Long categoryId;

  @NotNull
  @DecimalMin(value = "1")
  @DecimalMax(value = "99999")
  private BigDecimal price;

  @NotNull
  @Size(min = 1, max = 255)
  private String image;

  private String description;

  @OneOrZeroValid private Integer status;

  private List<DishFlavor> flavors;
}
