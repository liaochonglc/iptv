package com.ido.iptv.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @author Jun
 * @date 2018-09-25 15:15
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisConnection redisConnection(RedisConnectionFactory redisConnectionFactory) {
        return redisConnectionFactory.getConnection();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }
}
