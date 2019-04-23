package com.ej.sftp.spring.boot.starter.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ej.sftp.spring.boot.starter.client.SftpClient;
import com.ej.sftp.spring.boot.starter.exception.SftpConfigException;

/**
 * 
 * @Description: sftp多配置连接工厂类
 * @author Evan Jiang
 * @date 2019年4月22日 上午10:38:00
 *
 */
public class SftpClientFactory {

    private Map<String, SftpClient> clients = new ConcurrentHashMap<>();

    /**
     * 
     * @Description: 根据别名获取sftp连接
     * @author Evan Jiang
     * @date 2019年4月22日 上午10:39:38 
     * @param alias
     * @return
     */
    public SftpClient getClient(String alias) {
        if(alias == null) {
            return null;
        }
        return clients.get(alias);
    }

    /**
     * 
     * @Description: 缓存sftp连接
     * @author Evan Jiang
     * @date 2019年4月22日 上午10:38:33 
     * @param client sftp连接
     * @param cover 是否允许覆盖相同的别名
     */
    public void putClient(SftpClient client,
            boolean cover) {
        if (client == null) {
            return;
        }
        String alias = client.getAlias();
        if (!cover && clients.containsKey(alias)) {
            throw new SftpConfigException("sftp配置别名[alias:" + alias + "]存在重复");
        }
        clients.put(alias, client);
    }
}
