package com.ej.oss.spring.boot.starter.properties;

import com.ej.oss.spring.boot.starter.exception.OssConfigException;

public class OssConfig {

    private String alias;
    private String keyId;
    private String keySecret;
    private String endpoint;
    private String bucketName;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 
     * @Description: 多OssClient配置校验
     * @author Evan Jiang
     * @date 2019年4月23日 下午4:58:21
     */
    public void multiCheck() {
        if (alias == null || alias.trim().isEmpty()) {
            throw new OssConfigException(
                    this.getClass().getSimpleName() + "配置别名[alias]不能为空!");
        }
        singleCheck();
    }

    /**
     * 
     * @Description: 单OssClient配置校验
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:00:17
     */
    public void singleCheck() {
        if (keyId == null || keyId.trim().isEmpty()) {
            throw new OssConfigException(
                    this.getClass().getSimpleName() + "配置连接地址[keyId]不能为空!");
        }
        if (keySecret == null || keySecret.trim().isEmpty()) {
            throw new OssConfigException(
                    this.getClass().getSimpleName() + "配置连接用户名[keySecret]不能为空!");
        }
        if (endpoint == null || endpoint.trim().isEmpty()) {
            throw new OssConfigException(
                    this.getClass().getSimpleName() + "配置连接密码[endpoint]不能为空!");
        }
        if (bucketName == null || bucketName.trim().isEmpty()) {
            throw new OssConfigException(
                    this.getClass().getSimpleName() + "配置连接密码[bucketName]不能为空!");
        }
    }
    
    /**
     * 
     * @Description: 判断配置是否都为空,单个配置时使用,根据此方法确定是否要初始化OssClient
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:00:37 
     * @return
     */
    public boolean isBlank() {
        if (keyId != null && !keyId.trim().isEmpty()) {
            return Boolean.FALSE;
        }
        if (keySecret != null && !keySecret.trim().isEmpty()) {
            return Boolean.FALSE;
        }
        if (endpoint != null && !endpoint.trim().isEmpty()) {
            return Boolean.FALSE;
        }
        if (bucketName != null && !bucketName.trim().isEmpty()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
