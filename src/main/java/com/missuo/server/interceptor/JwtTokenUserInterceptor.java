package com.missuo.server.interceptor;

import com.missuo.common.constant.JwtClaimsConstant;
import com.missuo.common.constant.MessageConstant;
import com.missuo.common.context.BaseContext;
import com.missuo.common.exception.UserNotLoginException;
import com.missuo.common.properties.JwtProperties;
import com.missuo.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

  @Autowired private JwtProperties jwtProperties;
  @Autowired private JwtUtil jwtUtil;

  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    // Determine whether the currently intercepted method is the controller's method or other
    // resources
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    String token = request.getHeader(jwtProperties.getUserTokenName());

    try {
      log.info("Jwt verification:{}", token);
      Claims claims = jwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
      Long userID = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
      log.info("Current User ID: {}", userID);
      BaseContext.setCurrentId(userID);
      return true;
    } catch (Exception ex) {
      //            response.setStatus(401);
      //            return false;
      throw new UserNotLoginException(MessageConstant.USER_NOT_LOGIN);
    }
  }
}
