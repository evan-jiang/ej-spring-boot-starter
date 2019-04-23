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

    /**
     * 
     * @Description: 多SfptClient配置校验
     * @author Evan Jiang
     * @date 2019年4月23日 下午4:44:28
     */
    public void multiCheck() {
        if (alias == null || alias.trim().isEmpty()) {
            throw new SftpConfigException("sftp配置别名[alias]不能为空!");
        }
        singleCheck();
    }

    /**
     * 
     * @Description: 单SftpClient配置校验
     * @author Evan Jiang
     * @date 2019年4月23日 下午4:43:56
     */
    public void singleCheck() {
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

    /**
     * 
     * @Description: 判断配置是否都为空,单个配置时使用,根据此方法确定是否要初始化SftpClient
     * @author Evan Jiang
     * @date 2019年4月23日 下午4:41:54 
     * @return
     */
    public boolean isBlank() {
        if (host != null && !host.trim().isEmpty()) {
            return Boolean.FALSE;
        }
        if (port != null) {
            return Boolean.FALSE;
        }
        if (userName != null && !userName.trim().isEmpty()) {
            return Boolean.FALSE;
        }
        if (passWord != null && !passWord.trim().isEmpty()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
