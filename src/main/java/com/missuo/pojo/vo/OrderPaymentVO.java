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
public class OrderPaymentVO implements Serializable {

  private String nonceStr;
  private String paySign;
  private String timeStamp;
  private String signType;
  private String packageStr;
}
