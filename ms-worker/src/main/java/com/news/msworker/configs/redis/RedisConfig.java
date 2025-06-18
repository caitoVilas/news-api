package com.news.msworker.configs.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * Redis Configuration
 * This configuration sets up the Redis connection factory and operations for reactive use.
 *
 * @author caito
 *
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * Creates a ReactiveRedisConnectionFactory bean.
     *
     * @return a ReactiveRedisConnectionFactory for connecting to Redis
     */
    @Bean
    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(Objects.requireNonNull(host));
        redisStandaloneConfiguration.setPort(Integer.parseInt(Objects.requireNonNull(port)));
        redisStandaloneConfiguration.setPassword(Objects.requireNonNull(password));
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * Creates a ReactiveRedisOperations bean.
     *
     * @param reactiveRedisConnectionFactory the ReactiveRedisConnectionFactory to use
     * @return a ReactiveRedisOperations for performing operations on Redis
     */
    @Bean
    ReactiveRedisOperations<String, Object> redisOperations(
                                                ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, Object> context = builder
                .value(serializer)
                .hashKey(serializer)
                .hashValue(serializer)
                .build();
        return  new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }
}
