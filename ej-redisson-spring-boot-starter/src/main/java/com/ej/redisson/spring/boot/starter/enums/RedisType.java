package com.ej.redisson.spring.boot.starter.enums;

import com.ej.redisson.spring.boot.starter.exception.RedisConfigException;
import com.ej.redisson.spring.boot.starter.properties.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SentinelServersConfig;

public enum RedisType {

    SENTINEL() {
        public void checkConfig(RedisConfig redisConfig, Boolean needAlias) {
            super.checkConfig(redisConfig, needAlias);
            if (isBlank(redisConfig.getPassWord())) {
                throw new RedisConfigException("Redis配置[passWord]不能为空");
            }
            if (isBlank(redisConfig.getMasterName())) {
                throw new RedisConfigException("Redis配置[masterName]不能为空");
            }
            if (isBlank(redisConfig.getSentinelAddress())) {
                throw new RedisConfigException("Redis配置[sentinelAddress]不能为空");
            }
        }

        public RedissonClient create(RedisConfig redisConfig) {
            Config config = new Config();
            config.setCodec(new StringCodec());
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            String[] split = redisConfig.getSentinelAddress().split(SPLIT);
            for (String host : split) {
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
            super.checkConfig(redisConfig, needAlias);
            if (isBlank(redisConfig.getPassWord())) {
                throw new RedisConfigException("Redis配置[passWord]不能为空");
            }
            if (isBlank(redisConfig.getMasterAddress())) {
                throw new RedisConfigException("Redis配置[masterAddress]不能为空");
            }
            if (isBlank(redisConfig.getSlaveAddress())) {
                throw new RedisConfigException("Redis配置[slaveAddress]不能为空");
            }
        }

        @Override
        public RedissonClient create(RedisConfig redisConfig) {
            Config config = new Config();
            config.setCodec(new StringCodec());
            MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
            masterSlaveServersConfig.setPassword(redisConfig.getPassWord());
            masterSlaveServersConfig.setMasterAddress(redisConfig.getMasterAddress());
            String[] redisSlaveAddresses = redisConfig.getSlaveAddress().split(SPLIT);
            for (String redisSlaveAddress : redisSlaveAddresses) {
                masterSlaveServersConfig.addSlaveAddress(redisSlaveAddress);
            }
            return Redisson.create(config);
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

    /**
     * 参数校验
     * @param redisConfig
     * @param needAlias 是否需要别名
     * @auther: Evan·Jiang
     * @date: 2019/11/20 11:43
     */
    public void checkConfig(RedisConfig redisConfig, Boolean needAlias) {
        if(needAlias && isBlank(redisConfig.getAlias())){
            throw new RedisConfigException("Redis配置[alias]不能为空");
        }
    }

    /**
     * 生成相应的RedissonClient
     * @param redisConfig
     * @return org.redisson.api.RedissonClient
     * @auther: Evan·Jiang
     * @date: 2019/11/20 13:49
     */
    public RedissonClient create(RedisConfig redisConfig) {
        throw new RuntimeException("该类型[" + this.name() + "]的RedissonClient构造器没有实现");
    }

    protected boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    private static final String SPLIT = ";";
}
