package com.ej.sftp.spring.boot.starter.properties;

import com.ej.sftp.spring.boot.starter.exception.SftpConfigException;

public class SftpConfig {

    private String alias;
    private String host;
    private Integer port;
    private String userName;
    private String passWord;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    
    @Override
    public String toString() {
        return "SftpConfig [host=" + host + ", port=" + port + ", userName="
                + userName + "]";
    }

    public void multiConfigCheck() {
        if (alias == null || alias.trim().isEmpty()) {
            throw new SftpConfigException("sftp配置别名[alias]不能为空!");
        }
        if (host == null || host.trim().isEmpty()) {
            throw new SftpConfigException("sftp配置连接地址[host]不能为空!");
        }
        if (port == null) {
            throw new SftpConfigException("sftp配置连接端口[port]不能为空!");
        }
        if (userName == null || userName.trim().isEmpty()) {
            throw new SftpConfigException("sftp配置连接用户名[host]不能为空!");
        }
        if (passWord == null || passWord.trim().isEmpty()) {
            throw new SftpConfigException("sftp配置连接密码[host]不能为空!");
        }
    }

    public void singleConfigCheck() {
        if (host == null || host.trim().isEmpty()) {
            throw new SftpConfigException("sftp配置连接地址[host]不能为空!");
        }
        if (port == null) {
            throw new SftpConfigException("sftp配置连接端口[port]不能为空!");
        }
        if (userName == null || userName.trim().isEmpty()) {
            throw new SftpConfigException("sftp配置连接用户名[host]不能为空!");
        }
        if (passWord == null || passWord.trim().isEmpty()) {
            throw new SftpConfigException("sftp配置连接密码[host]不能为空!");
        }
    }
}
