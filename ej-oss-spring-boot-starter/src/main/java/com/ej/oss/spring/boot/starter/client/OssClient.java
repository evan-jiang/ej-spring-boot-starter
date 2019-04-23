package com.ej.oss.spring.boot.starter.client;

import java.io.File;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.ej.oss.spring.boot.starter.exception.OssFileNotFoundException;
import com.ej.oss.spring.boot.starter.properties.OssConfig;

/**
 * 
 * @Description: OSS客户端
 * @author Evan Jiang
 * @date 2019年4月22日 下午6:45:14
 *
 */
public class OssClient {

    private OssConfig ossConfig;

    private OSSClient ossClient;

    public String getAlias() {
        return ossConfig.getAlias();
    }

    /**
     * 初始化oss客户端
     * 
     * @param ossConfig
     * @param isMulti
     *            是否属多连接，判断是否需要校验连接的别名，用于工厂管理
     */
    public OssClient(OssConfig ossConfig, boolean isMulti) {
        if (isMulti) {
            ossConfig.multiCheck();
        } else {
            ossConfig.singleCheck();
        }
        this.ossConfig = ossConfig;
    }

    /**
     * 
     * @Description: 创建oss连接
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:06:16
     */
    private void initOssClient() {
        ossClient = (OSSClient) new OSSClientBuilder().build(
                ossConfig.getEndpoint(), ossConfig.getKeyId(),
                ossConfig.getKeySecret());
    }

    /**
     * 
     * @Description: 关闭oss连接
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:06:47
     */
    public void disconnect() {
        if (this.ossClient != null) {
            this.ossClient.shutdown();
        }
    }

    /**
     * 
     * @Description: 获取可用的oss连接
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:05:36
     */
    private OSSClient usableOssClient() {
        if (ossClient == null) {
            synchronized (this) {
                initOssClient();
            }
        }
        return ossClient;
    }

    /**
     * 
     * @Description: 判断oss上指定文件是否存在
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:07:20
     * @param key
     * @return
     */
    public boolean exists(String key) {
        return usableOssClient().doesObjectExist(ossConfig.getBucketName(),
                key);
    }

    /**
     * 
     * @Description: 下载文件
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:07:48
     * @param key
     * @param path
     * @return
     */
    public boolean downloadFile(String key, String path) {
        if (!exists(key)) {
            throw new OssFileNotFoundException("远程文件[" + key + "]不存在");
        }
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        usableOssClient().getObject(
                new GetObjectRequest(ossConfig.getBucketName(), key),
                new File(path));
        return true;
    }

    /**
     * 
     * @Description: 上传文件
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:08:08
     * @param key
     * @param path
     * @return
     */
    public boolean uploadFile(String key, String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new OssFileNotFoundException("本地文件[" + path + "]不存在");
        }
        usableOssClient().putObject(ossConfig.getBucketName(), key, file);
        return true;
    }

}
