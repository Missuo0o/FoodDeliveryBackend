package com.missuo.server.controller.notify;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.missuo.common.properties.WeChatProperties;
import com.missuo.server.service.OrderService;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
@Slf4j
public class PayNotifyController {
  @Autowired private OrderService orderService;
  @Autowired private WeChatProperties weChatProperties;

  @RequestMapping("/paySuccess")
  public void paySuccessNotify(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    // Read the request body
    String body = readData(request);

    // Decrypt the request body
    String plainText = decryptData(body);

    // Parse the decrypted request body
    JSONObject jsonObject = JSON.parseObject(plainText);
    String outTradeNo = jsonObject.getString("out_trade_no");
    String transactionId = jsonObject.getString("transaction_id");

    orderService.paySuccess(outTradeNo);

    responseToWeixin(response);
  }

  private String readData(HttpServletRequest request) throws Exception {
    BufferedReader reader = request.getReader();
    StringBuilder result = new StringBuilder();
    String line = null;
    while ((line = reader.readLine()) != null) {
      if (!result.isEmpty()) {
        result.append("\n");
      }
      result.append(line);
    }
    return result.toString();
  }

  private String decryptData(String body) throws Exception {
    JSONObject resultObject = JSON.parseObject(body);
    JSONObject resource = resultObject.getJSONObject("resource");
    String ciphertext = resource.getString("ciphertext");
    String nonce = resource.getString("nonce");
    String associatedData = resource.getString("associated_data");

    AesUtil aesUtil = new AesUtil(weChatProperties.getApiV3Key().getBytes(StandardCharsets.UTF_8));
    // Decrypt the data
    String plainText =
        aesUtil.decryptToString(
            associatedData.getBytes(StandardCharsets.UTF_8),
            nonce.getBytes(StandardCharsets.UTF_8),
            ciphertext);

    return plainText;
  }

  private void responseToWeixin(HttpServletResponse response) throws Exception {
    response.setStatus(200);
    HashMap<Object, Object> map = new HashMap<>();
    map.put("code", "SUCCESS");
    map.put("message", "SUCCESS");
    response.setHeader("Content-type", ContentType.APPLICATION_JSON.toString());
    response.getOutputStream().write(JSONUtils.toJSONString(map).getBytes(StandardCharsets.UTF_8));
    response.flushBuffer();
  }
}
