package com.missuo.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.missuo.common.properties.WeChatProperties;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeChatPayUtil {

  // WeChat payment order interface address
  public static final String JSAPI = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";

  // Refund application interface address
  public static final String REFUNDS = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";

  @Autowired private WeChatProperties weChatProperties;

  private CloseableHttpClient getClient() {
    PrivateKey merchantPrivateKey = null;
    try {
      merchantPrivateKey =
          PemUtil.loadPrivateKey(
              new FileInputStream(new File(weChatProperties.getPrivateKeyFilePath())));
      // Load platform certificate file
      X509Certificate x509Certificate =
          PemUtil.loadCertificate(
              new FileInputStream(new File(weChatProperties.getWeChatPayCertFilePath())));

      List<X509Certificate> wechatPayCertificates = Arrays.asList(x509Certificate);

      WechatPayHttpClientBuilder builder =
          WechatPayHttpClientBuilder.create()
              .withMerchant(
                  weChatProperties.getMchid(),
                  weChatProperties.getMchSerialNo(),
                  merchantPrivateKey)
              .withWechatPay(wechatPayCertificates);

      // The Http Client constructed through Wechat Pay Http Client Builder will automatically
      // handle signature and verification.
      CloseableHttpClient httpClient = builder.build();
      return httpClient;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private String post(String url, String body) throws Exception {
    CloseableHttpClient httpClient = getClient();

    HttpPost httpPost = new HttpPost(url);
    httpPost.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
    httpPost.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
    httpPost.addHeader("Wechatpay-Serial", weChatProperties.getMchSerialNo());
    httpPost.setEntity(new StringEntity(body, "UTF-8"));

    CloseableHttpResponse response = httpClient.execute(httpPost);
    try {
      String bodyAsString = EntityUtils.toString(response.getEntity());
      return bodyAsString;
    } finally {
      httpClient.close();
      response.close();
    }
  }

  private String get(String url) throws Exception {
    CloseableHttpClient httpClient = getClient();

    HttpGet httpGet = new HttpGet(url);
    httpGet.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
    httpGet.addHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
    httpGet.addHeader("Wechatpay-Serial", weChatProperties.getMchSerialNo());

    CloseableHttpResponse response = httpClient.execute(httpGet);
    try {
      String bodyAsString = EntityUtils.toString(response.getEntity());
      return bodyAsString;
    } finally {
      httpClient.close();
      response.close();
    }
  }

  private String jsapi(String orderNum, BigDecimal total, String description, String openid)
      throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("appid", weChatProperties.getAppid());
    jsonObject.put("mchid", weChatProperties.getMchid());
    jsonObject.put("description", description);
    jsonObject.put("out_trade_no", orderNum);
    jsonObject.put("notify_url", weChatProperties.getNotifyUrl());

    JSONObject amount = new JSONObject();
    amount.put(
        "total",
        total.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue());
    amount.put("currency", "CNY");

    jsonObject.put("amount", amount);

    JSONObject payer = new JSONObject();
    payer.put("openid", openid);

    jsonObject.put("payer", payer);

    String body = jsonObject.toJSONString();
    return post(JSAPI, body);
  }

  public JSONObject pay(String orderNum, BigDecimal total, String description, String openid)
      throws Exception {
    // Place orders in a unified manner and generate prepayment transaction orders
    String bodyAsString = jsapi(orderNum, total, description, openid);
    // Parse return results
    JSONObject jsonObject = JSON.parseObject(bodyAsString);
    System.out.println(jsonObject);

    String prepayId = jsonObject.getString("prepay_id");
    if (prepayId != null) {
      String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
      String nonceStr = RandomStringUtils.randomNumeric(32);
      ArrayList<Object> list = new ArrayList<>();
      list.add(weChatProperties.getAppid());
      list.add(timeStamp);
      list.add(nonceStr);
      list.add("prepay_id=" + prepayId);
      // Sign for the second time, and you need to sign again when transferring payment.
      StringBuilder stringBuilder = new StringBuilder();
      for (Object o : list) {
        stringBuilder.append(o).append("\n");
      }
      String signMessage = stringBuilder.toString();
      byte[] message = signMessage.getBytes();

      Signature signature = Signature.getInstance("SHA256withRSA");
      signature.initSign(
          PemUtil.loadPrivateKey(
              new FileInputStream(new File(weChatProperties.getPrivateKeyFilePath()))));
      signature.update(message);
      String packageSign = Base64.getEncoder().encodeToString(signature.sign());

      // Construct data to the WeChat applet to activate WeChat payment
      JSONObject jo = new JSONObject();
      jo.put("timeStamp", timeStamp);
      jo.put("nonceStr", nonceStr);
      jo.put("package", "prepay_id=" + prepayId);
      jo.put("signType", "RSA");
      jo.put("paySign", packageSign);

      return jo;
    }
    return jsonObject;
  }

  public String refund(String outTradeNo, String outRefundNo, BigDecimal refund, BigDecimal total)
      throws Exception {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("out_trade_no", outTradeNo);
    jsonObject.put("out_refund_no", outRefundNo);

    JSONObject amount = new JSONObject();
    amount.put(
        "refund",
        refund.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue());
    amount.put(
        "total",
        total.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue());
    amount.put("currency", "CNY");

    jsonObject.put("amount", amount);
    jsonObject.put("notify_url", weChatProperties.getRefundNotifyUrl());

    String body = jsonObject.toJSONString();

    // Call the refund application interface
    return post(REFUNDS, body);
  }
}
