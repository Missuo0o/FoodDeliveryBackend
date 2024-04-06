package com.missuo.common.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HttpClientUtil {

  static final int TIMEOUT_MSEC = 5 * 1000;
  private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

  public String doGet(String url, Map<String, String> paramMap) throws IOException {
    // Creating a Httpclient Object
    CloseableHttpClient httpClient = HttpClients.createDefault();

    String result = "";
    CloseableHttpResponse response = null;

    try {
      URIBuilder builder = new URIBuilder(url);
      if (paramMap != null) {
        for (String key : paramMap.keySet()) {
          builder.addParameter(key, paramMap.get(key));
        }
      }
      URI uri = builder.build();

      // Create a get request
      HttpGet httpGet = new HttpGet(uri);

      // Send Request
      response = httpClient.execute(httpGet);

      // Determine response status
      if (response.getStatusLine().getStatusCode() == 200) {
        result = EntityUtils.toString(response.getEntity(), "UTF-8");
      }
    } catch (Exception e) {
      log.error("doGet: {}", e.getMessage());
    } finally {
      if (response != null) {
        response.close();
      }
      httpClient.close();
    }

    return result;
  }

  public String doPost(String url, Map<String, String> paramMap) throws IOException {
    // Creating a Httpclient Object
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    String resultString = "";

    try {
      // Creating a Http Post Request
      HttpPost httpPost = new HttpPost(url);

      // Creating a Parameter List
      if (paramMap != null) {
        List<NameValuePair> paramList = new ArrayList<>();
        for (Map.Entry<String, String> param : paramMap.entrySet()) {
          paramList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        // Mock form
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
        httpPost.setEntity(entity);
      }

      httpPost.setConfig(builderRequestConfig());

      // Execute http request
      response = httpClient.execute(httpPost);

      resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
    } catch (Exception e) {
      log.error("doPost: {}", e.getMessage());
    } finally {
      if (response != null) {
        response.close();
      }
    }

    return resultString;
  }

  public String doPost4Json(String url, Map<String, String> paramMap) throws IOException {
    // Creating a Httpclient Object
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse response = null;
    String resultString = "";

    try {
      // Creating a Http Post Request
      HttpPost httpPost = new HttpPost(url);

      if (paramMap != null) {
        // Constructing data in json format
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(paramMap);
        StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
        // Set request encoding
        entity.setContentEncoding("utf-8");
        // Set data type
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
      }

      httpPost.setConfig(builderRequestConfig());

      // Execute http request
      response = httpClient.execute(httpPost);

      resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
    } catch (Exception e) {
      log.error("doPost4Json: {}", e.getMessage());
    } finally {
      if (response != null) {
        response.close();
      }
    }

    return resultString;
  }

  private RequestConfig builderRequestConfig() {
    return RequestConfig.custom()
        .setConnectTimeout(TIMEOUT_MSEC)
        .setConnectionRequestTimeout(TIMEOUT_MSEC)
        .setSocketTimeout(TIMEOUT_MSEC)
        .build();
  }
}
