package com.ej.oss.spring.boot.starter.configuration;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ej.oss.spring.boot.starter.client.OssClient;
import com.ej.oss.spring.boot.starter.factory.OssClientFactory;
import com.ej.oss.spring.boot.starter.properties.OssConfig;
import com.ej.oss.spring.boot.starter.properties.OssProperties;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {

    @Resource
    private OssProperties ossProperties;

    @Bean
    public OssClientFactory ossClientFactory() {
        List<OssConfig> multi = ossProperties.getMulti();
        if (!multi.isEmpty()) {
            OssClientFactory clientFactory = new OssClientFactory();
            ossProperties.multiConfigCheck();
            multi.forEach(sftp -> {
                OssClient client = new OssClient(sftp);
                String alias = sftp.getAlias();
                clientFactory.putClient(alias, client, false);
            });
            return clientFactory;
        }
        return null;
    }

    @Bean
    public OssClient ossClient() {
        if (!ossProperties.singleIsBlank()) {
            ossProperties.singleConfigCheck();
            OssClient client = new OssClient(ossProperties.getSingle());
            return client;
        }
        return null;
    }
}
