package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class OrdersRejectionDTO implements Serializable {

  @NotNull private Long id;

  @NotNull
  @Size(min = 1, max = 50)
  private String rejectionReason;
}
