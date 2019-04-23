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

    public OssClient(OssConfig ossConfig) {
        this.ossConfig = ossConfig;
    }

    private void initOssClient() {
        ossClient = (OSSClient) new OSSClientBuilder().build(
                ossConfig.getEndpoint(), ossConfig.getKeyId(),
                ossConfig.getKeySecret());
    }

    public void disconnect() {
        if (this.ossClient != null) {
            this.ossClient.shutdown();
        }
    }

    private OSSClient usableOssClient() {
        if (ossClient == null) {
            synchronized (this) {
                initOssClient();
            }
        }
        return ossClient;
    }

    public boolean exists(String key) {
        return usableOssClient().doesObjectExist(ossConfig.getBucketName(),
                key);
    }

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

    public boolean uploadFile(String key, String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new OssFileNotFoundException("本地文件[" + path + "]不存在");
        }
        usableOssClient().putObject(ossConfig.getBucketName(), key, file);
        return true;
    }

}
