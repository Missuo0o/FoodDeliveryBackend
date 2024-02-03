package com.missuo.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
  @Bean
  public RedisTemplate<Object, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());

    redisTemplate.setConnectionFactory(redisConnectionFactory);
    return redisTemplate;
  }
}
