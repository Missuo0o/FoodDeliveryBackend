package com.missuo.pojo.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ShoppingCartDTO implements Serializable {

  private Long dishId;

  private Long setmealId;

  private String dishFlavor;
}
