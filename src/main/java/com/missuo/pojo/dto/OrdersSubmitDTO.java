package com.missuo.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class OrdersSubmitDTO implements Serializable {
  @NotNull private Long addressBookId;

  @NotNull
  @Range(min = 1, max = 2)
  private Integer payMethod;

  private String remark;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @NotNull
  private LocalDateTime estimatedDeliveryTime;

  @OneOrZeroValid
  // Delivery status 1Send immediately 0Select a specific time
  private Integer deliveryStatus;

  // Tableware quantity status 1 Provide according to meal size 0 Select specific quantity
  @OneOrZeroValid private Integer tablewareStatus;

  @NotNull
  @Range(min = 0, max = 10)
  private Integer tablewareNumber;

  @NotNull
  @Range(min = 1, max = 10)
  private Integer packAmount;

  @NotNull
  @DecimalMin(value = "0.01")
  @DecimalMax(value = "99999.99")
  private BigDecimal amount;
}
