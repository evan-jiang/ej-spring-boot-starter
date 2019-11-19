package com.ej.redisson.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

@ConfigurationProperties(prefix = "ej.redis")
public class RedisProperties {

    @NestedConfigurationProperty
    private List<RedisConfig> multi;
    private RedisConfig single;

    public List<RedisConfig> getMulti() {
        return multi;
    }

    public void setMulti(List<RedisConfig> multi) {
        this.multi = multi;
    }

    public RedisConfig getSingle() {
        return single;
    }

    public void setSingle(RedisConfig single) {
        this.single = single;
    }

}
