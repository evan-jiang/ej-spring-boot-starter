package com.ej.redisson.spring.boot.starter.enums;

import com.ej.redisson.spring.boot.starter.exception.RedisConfigException;
import com.ej.redisson.spring.boot.starter.properties.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;

public enum RedisType {

    SENTINEL() {
        public void checkConfig(RedisConfig redisConfig, Boolean needAlias) {
            if(needAlias && isBlank(redisConfig.getAlias())){
                throw new RedisConfigException("Redis配置[alias]不能为空");
            }
            if(isBlank(redisConfig.getMasterName())){
                throw new RedisConfigException("Redis配置[masterName]不能为空");
            }
            if(isBlank(redisConfig.getSentinelAddress())){
                throw new RedisConfigException("Redis配置[sentinelAddress]不能为空");
            }
            if(isBlank(redisConfig.getPassWord())){
                throw new RedisConfigException("Redis配置[passWord]不能为空");
            }
        }
        public RedissonClient create(RedisConfig redisConfig) {
            Config config = new Config();
            config.setCodec(new StringCodec());
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            String[] split = redisConfig.getSentinelAddress().split(SPLIT);
            for(String host:split){
                sentinelServersConfig.addSentinelAddress(host);
            }
            sentinelServersConfig.setMasterName(redisConfig.getMasterName());
            sentinelServersConfig.setPassword(redisConfig.getPassWord());
            sentinelServersConfig.setDatabase(redisConfig.getDataBase());
            return Redisson.create(config);
        }
    },
    MASTER_SLAVE() {
        public void checkConfig(RedisConfig redisConfig, Boolean needAlias) {

        }
    },
    SINGLE() {
        public void checkConfig(RedisConfig redisConfig, Boolean needAlias) {
            throw new RedisConfigException("暂不支持连接Redis单点");
        }
    },
    CLUSTER() {
        public void checkConfig(RedisConfig redisConfig, Boolean needAlias) {
            throw new RedisConfigException("暂不支持连接Redis集群");
        }
    };

    public abstract void checkConfig(RedisConfig redisConfig, Boolean needAlias);

    public RedissonClient create(RedisConfig redisConfig) {
        throw new RuntimeException("该类型[" + this.name() + "]的RedissonClient构造器没有实现");
    }

    protected boolean isBlank(String value){
        return value == null || value.trim().length() == 0;
    }

    private static final String SPLIT = ";";
}
