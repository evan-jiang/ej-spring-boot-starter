package com.ej.oss.spring.boot.starter.dto;

import java.io.Serializable;

/**
 * 
 * @Description: Acs信息实体
 * @author Evan Jiang
 * @date 2019年4月23日 下午12:02:42 
 *
 */
public class AcsInfoDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String endpoint;
    private String bucket;
    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken;
    private long expiration;
    public String getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getBucket() {
        return bucket;
    }
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    public String getAccessKeyId() {
        return accessKeyId;
    }
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    public String getAccessKeySecret() {
        return accessKeySecret;
    }
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
    public String getSecurityToken() {
        return securityToken;
    }
    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }
    public long getExpiration() {
        return expiration;
    }
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
