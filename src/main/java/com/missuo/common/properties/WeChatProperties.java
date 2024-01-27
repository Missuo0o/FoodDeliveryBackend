package com.missuo.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WeChatProperties {

  private String appid; // The appid of the mini program
  private String secret; // Mini program secret key
  private String mchid; // business number
  private String mchSerialNo; // /Certificate serial number of merchant api certificate
  private String privateKeyFilePath; // Merchant private key file
  private String apiV3Key; // Certificate decryption key
  private String weChatPayCertFilePath; // Platform certificate
  private String notifyUrl; // Callback address for successful payment
  private String refundNotifyUrl; // Callback address for successful refund
}
