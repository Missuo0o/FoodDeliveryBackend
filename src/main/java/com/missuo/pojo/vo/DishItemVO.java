package com.missuo.pojo.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishItemVO implements Serializable {

  private String name;

  private Integer copies;

  private String image;

  private String description;
}
