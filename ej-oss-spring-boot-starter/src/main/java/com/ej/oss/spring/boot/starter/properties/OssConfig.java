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

    public void multiConfigCheck() {
        if (alias == null || alias.trim().isEmpty()) {
            throw new OssConfigException(
                    this.getClass().getSimpleName() + "配置别名[alias]不能为空!");
        }
        singleConfigCheck();
    }

    public void singleConfigCheck() {
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
}
