package com.missuo.server.controller.user;

import com.missuo.common.constant.JwtClaimsConstant;
import com.missuo.common.constant.RedisConstant;
import com.missuo.common.properties.JwtProperties;
import com.missuo.common.result.Result;
import com.missuo.common.utils.JwtUtil;
import com.missuo.pojo.dto.UserLoginDTO;
import com.missuo.pojo.entity.User;
import com.missuo.pojo.vo.UserLoginVO;
import com.missuo.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/user")
@Tag(name = "User Management")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;
  private final JwtProperties jwtProperties;
  private final JwtUtil jwtUtil;
  private final RedisTemplate<Object, Object> redisTemplate;

  @PostMapping("/login")
  @Operation(summary = "User Login")
  public Result login(@Validated @RequestBody UserLoginDTO userLoginDTO) throws IOException {
    log.info("User Login：{}", userLoginDTO);
    User user = userService.vxLogin(userLoginDTO);

    Map<String, Object> claims = new HashMap<>();
    claims.put(JwtClaimsConstant.USER_ID, user.getId());

    String token =
        jwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

    UserLoginVO userLoginVO =
        UserLoginVO.builder().id(user.getId()).openid(user.getOpenid()).token(token).build();

    redisTemplate
        .opsForValue()
        .set("User_id" + user.getId(), user.getId(), RedisConstant.REDIS_EXPIRE, TimeUnit.HOURS);
    return Result.success(userLoginVO);
  }
}
