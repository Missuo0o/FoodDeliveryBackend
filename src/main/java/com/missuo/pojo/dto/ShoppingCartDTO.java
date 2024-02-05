package com.missuo.pojo.dto;

import com.missuo.server.annotation.AtLeastOneNotNull;
import java.io.Serializable;
import lombok.Data;

@Data
@AtLeastOneNotNull
public class ShoppingCartDTO implements Serializable {

  private Long dishId;

  private Long setmealId;

  private String dishFlavor;
}
