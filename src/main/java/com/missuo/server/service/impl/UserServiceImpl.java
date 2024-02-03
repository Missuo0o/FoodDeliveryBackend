package com.missuo.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.missuo.common.constant.MessageConstant;
import com.missuo.common.constant.WeChatConstant;
import com.missuo.common.exception.LoginFailedException;
import com.missuo.common.properties.WeChatProperties;
import com.missuo.common.utils.HttpClientUtil;
import com.missuo.pojo.dto.UserLoginDTO;
import com.missuo.pojo.entity.User;
import com.missuo.server.mapper.UserMapper;
import com.missuo.server.service.UserService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private HttpClientUtil httpClientUtil;
  @Autowired private WeChatProperties weChatProperties;
  @Autowired private UserMapper userMapper;

  @Override
  public User vxLogin(UserLoginDTO userLoginDTO) {
    Map<String, String> map = new HashMap<>();

    map.put(WeChatConstant.APP_ID, weChatProperties.getAppid());
    map.put(WeChatConstant.SECRET, weChatProperties.getSecret());
    map.put(WeChatConstant.JS_CODE, userLoginDTO.getCode());
    map.put(WeChatConstant.GRANT_TYPE, WeChatConstant.GRANT_TYPE_VALUE);

    String json = httpClientUtil.doGet(WeChatConstant.WECHAT_API, map);

    JSONObject jsonObject = JSON.parseObject(json);
    String openid = jsonObject.getString(WeChatConstant.OPEN_ID);

    if (openid == null) {
      throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
    }

    User user = userMapper.getByOpenid(openid);
    if (user == null) {
      user = User.builder().openid(openid).createTime(LocalDateTime.now()).build();
      userMapper.insert(user);
    }

    return user;
  }
}
