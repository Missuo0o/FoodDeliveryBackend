package com.missuo.server.controller.user;

import com.missuo.common.constant.RedisConstant;
import com.missuo.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Tag(name = "Shop Management")
@Slf4j
public class ShopController {

  @Autowired private RedisTemplate<Object, Object> redisTemplate;

  @GetMapping("/status")
  @Operation(summary = "Get Shop Status")
  public Result getStatus() {
    Integer status = (Integer) redisTemplate.opsForValue().get(RedisConstant.REDIS_KEY);
    log.info("Set Shop Status：{}", Objects.equals(status, 1) ? "Open" : "Closed");
    return Result.success(status);
  }
}
