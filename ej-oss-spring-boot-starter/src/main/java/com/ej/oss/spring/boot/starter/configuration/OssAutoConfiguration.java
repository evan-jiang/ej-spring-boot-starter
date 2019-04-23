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
            multi.forEach(config -> {
                OssClient client = new OssClient(config, Boolean.TRUE);
                clientFactory.putClient(client, Boolean.FALSE);
            });
            return clientFactory;
        }
        return null;
    }

    @Bean
    public OssClient ossClient() {
        OssConfig config = ossProperties.getSingle();
        if (!config.isBlank()) {
            OssClient client = new OssClient(config, Boolean.FALSE);
            return client;
        }
        return null;
    }
}
