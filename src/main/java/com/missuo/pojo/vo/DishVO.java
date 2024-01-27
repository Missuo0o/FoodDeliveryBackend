package com.missuo.pojo.vo;

import com.missuo.pojo.entity.DishFlavor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishVO implements Serializable {

  private Long id;

  private String name;

  private Long categoryId;

  private BigDecimal price;

  private String image;

  private String description;

  // 0 Stop selling 1 Start selling
  private Integer status;

  private LocalDateTime updateTime;

  private String categoryName;

  private List<DishFlavor> flavors = new ArrayList<>();

  // private Integer copies;
}
