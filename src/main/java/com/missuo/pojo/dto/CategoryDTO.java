package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CategoryDTO implements Serializable {

  private Long id;

  @NotNull private Integer type;

  @NotNull
  @Size(min = 1, max = 20)
  private String name;

  @NotNull
  @Range(min = 0, max = 99)
  private Integer sort;
}
