package com.ej.redisson.spring.boot.starter.factory;

import com.ej.redisson.spring.boot.starter.exception.RedisConfigException;
import org.redisson.api.RedissonClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedissonClientFactory {
    private Map<String, RedissonClient> clients = new ConcurrentHashMap<>();

    public RedissonClient getClient(String alias) {
        if (alias == null) {
            return null;
        }
        return clients.get(alias);
    }

    public void putClient(String alias, RedissonClient client) {
        if (client == null) {
            return;
        }
        if (clients.containsKey(alias)) {
            throw new RedisConfigException("redis配置别名[alias:" + alias + "]存在重复");
        }
        clients.put(alias, client);
    }
}
