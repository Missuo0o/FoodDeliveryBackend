package com.missuo.pojo.entity;

import com.missuo.server.annotation.OneOrZeroValid;
import jakarta.validation.constraints.NotBlank;
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

  @NotBlank
  @Size(max = 12)
  private String consignee;

  @NotBlank
  @Size(min = 10, max = 10)
  private String phone;

  @OneOrZeroValid private Integer sex;

  @NotBlank
  @Size(max = 200)
  private String detail;

  @NotBlank
  @Size(max = 20)
  private String label;

  // 0 no 1 yes
  private Integer isDefault;
}
