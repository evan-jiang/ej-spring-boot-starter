package com.ej.sftp.spring.boot.starter.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.ej.sftp.spring.boot.starter.exception.SftpConnectionException;
import com.ej.sftp.spring.boot.starter.exception.SftpFileNotFoundException;
import com.ej.sftp.spring.boot.starter.exception.SftpUnknownException;
import com.ej.sftp.spring.boot.starter.properties.SftpConfig;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

/**
 * 
 * @Description: sftp客户端
 * @author Evan Jiang
 * @date 2019年4月22日 下午2:42:04
 *
 */
public class SftpClient {

    private SftpConfig sftpConfig;
    private ChannelSftp sftp;
    private Session session;

    public SftpClient(SftpConfig sftpConfig) {
        this.sftpConfig = sftpConfig;
    }

    /**
     * 
     * @Description: 获取可用的sftp连接
     * @author Evan Jiang
     * @date 2019年4月22日 下午2:33:56
     * @return
     */
    private ChannelSftp usableSftp() {
        if (sftp == null || !sftp.isConnected() || session == null
                || !session.isConnected()) {
            synchronized (this) {
                if (sftp == null || !sftp.isConnected() || session == null
                        || !session.isConnected()) {
                    connect();
                }
            }
        }
        return sftp;
    }

    /**
     * 
     * @Description: 创建sftp连接
     * @author Evan Jiang
     * @date 2019年4月22日 下午2:37:35
     * @throws SftpConnectionException
     */
    private void connect() throws SftpConnectionException {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(sftpConfig.getUserName(),
                    sftpConfig.getHost(), sftpConfig.getPort());
            session.setPassword(sftpConfig.getPassWord());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect(5000);
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            throw new SftpConnectionException(sftpConfig.toString() + "连接异常",
                    e);
        }
    }

    /**
     * 
     * @Description: 关闭sftp连接
     * @author Evan Jiang
     * @date 2019年4月22日 下午2:37:56
     */
    public void disconnect() {
        if (sftp != null && sftp.isConnected()) {
            sftp.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    /**
     * 
     * @Description: 判断sftp上指定文件是否存在
     * @author Evan Jiang
     * @date 2019年4月22日 下午2:55:11 
     * @param key
     * @return
     * @throws SftpConnectionException
     * @throws SftpUnknownException
     */
    public boolean exists(String key)
            throws SftpConnectionException, SftpUnknownException {
        try {
            SftpATTRS attrs = usableSftp().lstat(key);
            if (attrs == null) {
                return false;
            }
            return true;
        } catch (SftpException e) {
            if (e.getMessage().contains("No such file")) {
                return false;
            }
            throw new SftpUnknownException(e);
        }
    }

    /**
     * 
     * @Description: 下载文件
     * @author Evan Jiang
     * @date 2019年4月22日 下午2:55:24 
     * @param key
     * @param path
     * @return
     * @throws SftpConnectionException
     * @throws SftpFileNotFoundException
     * @throws SftpUnknownException
     */
    public boolean downloadFile(String key, String path)
            throws SftpConnectionException, SftpFileNotFoundException,
            SftpUnknownException {
        if (!exists(key)) {
            throw new SftpFileNotFoundException("远程文件[" + key + "]不存在");
        }
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try (FileOutputStream fileOuts = new FileOutputStream(file)) {
            usableSftp().get(key, fileOuts);
            return true;
        } catch (Exception e) {
            throw new SftpUnknownException(e);
        }
    }

    /**
     * 
     * @Description: 上传文件
     * @author Evan Jiang
     * @date 2019年4月22日 下午2:55:39 
     * @param key
     * @param path
     * @return
     * @throws SftpConnectionException
     * @throws SftpFileNotFoundException
     * @throws SftpUnknownException
     */
    public boolean uploadFile(String key, String path)
            throws SftpConnectionException, SftpFileNotFoundException,
            SftpUnknownException {
        File file = new File(path);
        if (!file.exists()) {
            throw new SftpFileNotFoundException("本地文件[" + path + "]不存在");
        }
        try (FileInputStream fileIns = new FileInputStream(file)) {
            usableSftp().put(fileIns, key);
            return true;
        } catch (Exception e) {
            throw new SftpUnknownException(e);
        }
    }
}
