package com.xkcoding.geo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration //
public class AppConfig {

  @Value("${spring.redis.port:6379}")
  int port;
  @Value("${spring.redis.host:127.0.0.1}")
  private String host;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(port);
    JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
    redisConnectionFactory.afterPropertiesSet();
    return redisConnectionFactory;
  }


  @Bean
  public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate redisTemplate = new RedisTemplate();
    redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8); // key -- string
    redisTemplate.setConnectionFactory(redisConnectionFactory); //
    // redisTemplate.setValueSerializer();

    // 必须执行这个函数，这个函数的作用是初始化参数和初识化工作
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
