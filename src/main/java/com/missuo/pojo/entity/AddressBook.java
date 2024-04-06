package com.missuo.pojo.entity;

import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
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
  @Serial private static final long serialVersionUID = 1;

  private Long id;

  private Long userId;

  @NotNull
  @Size(min = 1, max = 12)
  private String consignee;

  @NotNull
  @Size(min = 10, max = 10)
  private String phone;

  @OneOrZeroValid private Integer sex;

  @NotNull
  @Size(min = 1, max = 200)
  private String detail;

  @NotNull
  @Size(min = 1, max = 20)
  private String label;

  // 0 no 1 yes
  private Integer isDefault;
}
