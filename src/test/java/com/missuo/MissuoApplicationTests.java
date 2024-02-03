package com.missuo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class MissuoApplicationTests {
  @Autowired RedisTemplate<Object, Object> redisTemplate;

  @Test
  void contextLoads() {
    redisTemplate.delete(redisTemplate.keys("*"));
  }
}
