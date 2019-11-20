package com.ej.redisson.spring.boot.starter.client;

import org.redisson.Redisson;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class EjRedisClient extends Redisson {

    protected EjRedisClient(Config config) {
        super(config);
    }

    public static EjRedisClient create(Config config) {
        EjRedisClient ejRedisClient = new EjRedisClient(config);
        if (config.isRedissonReferenceEnabled()) {
            ejRedisClient.enableRedissonReferenceSupport();
        }
        return ejRedisClient;
    }

    public String getString(String key){
        RBinaryStream binaryStream = super.getBinaryStream(key);
        if(binaryStream == null){
            return null;
        }
        byte[] bytes = binaryStream.get();
        if(bytes == null){
            return null;
        }
        return new String(bytes);
    }
}
