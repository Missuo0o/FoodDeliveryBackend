package com.missuo.server.controller.admin;

import com.missuo.common.constant.MessageConstant;
import com.missuo.common.constant.RedisConstant;
import com.missuo.common.exception.IllegalException;
import com.missuo.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Tag(name = "Shop Management")
@Slf4j
public class ShopController {

  @Autowired private RedisTemplate<String, String> redisTemplate;

  @PutMapping("/{status}")
  @Operation(summary = "Set Shop Status")
  public Result setStatus(@PathVariable Integer status) {
    if (status == null || (status != 1 && status != 0)) {
      throw new IllegalException(MessageConstant.ILLEGAL_OPERATION);
    }
    log.info("Set Shop Status：{}", status == 1 ? "Open" : "Closed");
    redisTemplate.opsForValue().set(RedisConstant.REDIS_KEY, String.valueOf(status));
    return Result.success();
  }

  @GetMapping("/status")
  @Operation(summary = "Get Shop Status")
  public Result getStatus() {
    Integer status =
        Integer.valueOf(
            Objects.requireNonNull(redisTemplate.opsForValue().get(RedisConstant.REDIS_KEY)));
    log.info("Set Shop Status：{}", Objects.equals(status, 1) ? "Open" : "Closed");
    return Result.success(status);
  }
}
