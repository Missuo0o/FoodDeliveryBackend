package com.missuo.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class OrdersRejectionDTO implements Serializable {

  @NotNull private Long id;

  @NotBlank
  @Size(max = 50)
  private String rejectionReason;
}
