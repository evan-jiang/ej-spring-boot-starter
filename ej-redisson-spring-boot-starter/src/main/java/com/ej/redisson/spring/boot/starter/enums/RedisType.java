package com.ej.redisson.spring.boot.starter.enums;

import com.ej.redisson.spring.boot.starter.client.EjRedisClient;
import com.ej.redisson.spring.boot.starter.exception.RedisConfigException;
import com.ej.redisson.spring.boot.starter.properties.RedisConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;

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

        @Override
        protected Config buildConfig(RedisConfig redisConfig) {
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
            return config;
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
        protected Config buildConfig(RedisConfig redisConfig) {
            Config config = new Config();
            config.setCodec(new StringCodec());
            MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers();
            masterSlaveServersConfig.setPassword(redisConfig.getPassWord());
            masterSlaveServersConfig.setDatabase(redisConfig.getDataBase());
            masterSlaveServersConfig.setMasterAddress(redisConfig.getMasterAddress());
            String[] redisSlaveAddresses = redisConfig.getSlaveAddress().split(SPLIT);
            for (String redisSlaveAddress : redisSlaveAddresses) {
                masterSlaveServersConfig.addSlaveAddress(redisSlaveAddress);
            }
            return config;
        }
    },
    SINGLE() {
        public void checkConfig(RedisConfig redisConfig, Boolean needAlias) {
            super.checkConfig(redisConfig, needAlias);
            if (isBlank(redisConfig.getPassWord())) {
                throw new RedisConfigException("Redis配置[passWord]不能为空");
            }
            if (isBlank(redisConfig.getSingleAddress())) {
                throw new RedisConfigException("Redis配置[singleAddress]不能为空");
            }
        }

        @Override
        protected Config buildConfig(RedisConfig redisConfig) {
            Config config = new Config();
            config.setCodec(new StringCodec());
            SingleServerConfig singleServerConfig = config.useSingleServer();
            singleServerConfig.setAddress(redisConfig.getSingleAddress());
            singleServerConfig.setPassword(redisConfig.getPassWord());
            singleServerConfig.setDatabase(redisConfig.getDataBase());
            return config;
        }
    },
    CLUSTER() {

    };

    /**
     * 参数校验
     *
     * @param redisConfig
     * @param needAlias   是否需要别名
     * @auther: Evan·Jiang
     * @date: 2019/11/20 11:43
     */
    public void checkConfig(RedisConfig redisConfig, Boolean needAlias) {
        if (needAlias && isBlank(redisConfig.getAlias())) {
            throw new RedisConfigException("Redis配置[alias]不能为空");
        }
    }

    protected Config buildConfig(RedisConfig redisConfig){
        throw new RuntimeException("该类型[" + this.name() + "]的RedissonClient构造器没有实现");
    }

    public RedissonClient create(RedisConfig redisConfig) {
        Config config = buildConfig(redisConfig);
        return Redisson.create(config);
    }

    public EjRedisClient createEjRedisClient(RedisConfig redisConfig) {
        Config config = buildConfig(redisConfig);
        return EjRedisClient.create(config);
    }

    protected boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    private static final String SPLIT = ";";
}
