package com.missuo.pojo.vo;

import com.missuo.pojo.entity.SetmealDish;
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
public class SetmealVO implements Serializable {

  private Long id;

  private Long categoryId;

  private String name;

  private BigDecimal price;

  // 0 Disable 1 Enable
  private Integer status;

  private String description;

  private String image;

  private LocalDateTime updateTime;

  private String categoryName;

  private List<SetmealDish> setmealDishes = new ArrayList<>();
}
