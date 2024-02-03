package com.missuo.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

  /** Configuration related to jwt token generation for employees on the management side */
  private String adminSecretKey;

  private Long adminTtl;

  private String adminTokenName;

  /** User-side WeChat user generation jwt token related configuration */
  private String userSecretKey;

  private Long userTtl;

  private String userTokenName;
}
