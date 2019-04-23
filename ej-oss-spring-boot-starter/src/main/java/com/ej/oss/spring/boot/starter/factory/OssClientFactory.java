package com.ej.oss.spring.boot.starter.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ej.oss.spring.boot.starter.client.OssClient;
import com.ej.oss.spring.boot.starter.exception.OssConfigException;

public class OssClientFactory {

    private Map<String, OssClient> clients = new ConcurrentHashMap<>();

    public OssClient getClient(String alias) {
        if (alias == null) {
            return null;
        }
        return clients.get(alias);
    }

    public void putClient(OssClient client, boolean cover) {
        if (client == null) {
            return;
        }
        String alias = client.getAlias();
        if (!cover && clients.containsKey(alias)) {
            throw new OssConfigException(
                    clients.get(alias).getClass().getSimpleName()
                            + "配置别名[alias:" + alias + "]存在重复");
        }
        clients.put(alias, client);
    }
}
