package com.missuo.pojo.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long userId;

  private String consignee;

  private String phone;

  private Integer sex;

  private String cityName;

  private String stateName;

  private String zipCode;

  private String detail;

  private String label;

  // 0 no 1 yes
  private Integer isDefault;
}
