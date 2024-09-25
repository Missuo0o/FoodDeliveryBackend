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
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

  private final JwtProperties jwtProperties;
  private final JwtUtil jwtUtil;
  private final RedisTemplate<Object, Object> redisTemplate;

  public boolean preHandle(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler) {
    // Determine whether the currently intercepted method is the controller's method or other
    // resources
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    String token = request.getHeader(jwtProperties.getAdminTokenName());

    try {
      Claims claims = jwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
      Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
      Long id = (Long) redisTemplate.opsForValue().get("Employee_id" + empId);

      if (id == null) {
        throw new UserNotLoginException(MessageConstant.USER_NOT_LOGIN);
      } else {
        BaseContext.setCurrentId(empId);

        long currentTimeMillis = System.currentTimeMillis();
        long expirationTime = claims.getExpiration().getTime();
        long remainingTime = expirationTime - currentTimeMillis;
        if (remainingTime < 5 * 60 * 1000) {
          String newToken =
              jwtUtil.createJWT(
                  jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);
          response.setHeader(jwtProperties.getAdminTokenName(), newToken);
          redisTemplate.expire(
              "Employee_id" + empId,
              2,
              TimeUnit.HOURS);
        }
        return true;
      }

    } catch (Exception ex) {
      throw new UserNotLoginException(MessageConstant.USER_NOT_LOGIN);
    }
  }

  @Override
  public void afterCompletion(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull Object handler,
      Exception ex) {
    BaseContext.removeCurrentId();
  }
}
