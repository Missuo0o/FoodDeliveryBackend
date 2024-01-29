package com.missuo.server.config;

import com.missuo.common.properties.AliOssProperties;
import com.missuo.common.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {
  @Bean
  @ConditionalOnMissingBean
  public AliOssUtil ossUtil(AliOssProperties aliOssProperties) {
    log.info("AliOssProperties:{}", aliOssProperties);
    return new AliOssUtil(
        aliOssProperties.getEndpoint(),
        aliOssProperties.getAccessKeyId(),
        aliOssProperties.getAccessKeySecret(),
        aliOssProperties.getBucketName());
  }
}
