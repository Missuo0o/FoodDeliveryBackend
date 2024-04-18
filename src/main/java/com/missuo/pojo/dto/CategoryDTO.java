package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CategoryDTO implements Serializable {

  @NotNull(groups = Update.class)
  private Long id;

  @NotNull private Integer type;

  @NotBlank
  @Size(max = 20)
  private String name;

  @NotNull
  @Range(max = 99)
  private Integer sort;

  public interface Update extends Default {}
}
