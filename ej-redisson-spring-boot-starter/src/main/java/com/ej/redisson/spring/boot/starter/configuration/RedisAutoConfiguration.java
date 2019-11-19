package com.ej.redisson.spring.boot.starter.configuration;

import com.ej.redisson.spring.boot.starter.factory.RedissonClientFactory;
import com.ej.redisson.spring.boot.starter.properties.RedisConfig;
import com.ej.redisson.spring.boot.starter.properties.RedisProperties;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        RedisConfig redisConfig = redisProperties.getSingle();
        if (redisConfig == null) {
            return null;
        }
        redisConfig.getRedisType().checkConfig(redisConfig, Boolean.FALSE);
        return redisConfig.getRedisType().create(redisConfig);
    }

    @Bean
    public RedissonClientFactory redissonClientFactory() {
        List<RedisConfig> multi = redisProperties.getMulti();
        if (multi == null || multi.isEmpty()) {
            return null;
        }
        RedissonClientFactory redissonClientFactory = new RedissonClientFactory();
        multi.stream().forEach(redisConfig -> {
            redisConfig.getRedisType().checkConfig(redisConfig, Boolean.TRUE);
            redissonClientFactory.putClient(redisConfig.getAlias(), redisConfig.getRedisType().create(redisConfig));
        });
        return redissonClientFactory;
    }
}
