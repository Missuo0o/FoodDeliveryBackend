package com.missuo.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alioss")
@Data
public class AliOssProperties {

  private String endpoint;
  private String accessKeyId;
  private String accessKeySecret;
  private String bucketName;
}
