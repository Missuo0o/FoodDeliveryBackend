package com.missuo.pojo.dto;

import com.missuo.pojo.entity.SetmealDish;
import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class SetmealDTO implements Serializable {

  @NotNull(groups = Update.class)
  private Long id;

  @NotNull
  @Min(value = 1)
  private Long categoryId;

  @NotBlank
  @Size(max = 20)
  private String name;

  @NotNull
  @DecimalMin(value = "0.01")
  @DecimalMax(value = "99999.99")
  private BigDecimal price;

  @OneOrZeroValid private Integer status;

  private String description;

  @NotBlank
  @Size(max = 255)
  private String image;

  @NotEmpty private List<SetmealDish> setmealDishes;

  public interface Update extends Default {}
}
