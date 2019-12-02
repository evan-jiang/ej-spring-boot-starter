package com.ej.oss.spring.boot.starter.configuration;

import com.ej.oss.spring.boot.starter.client.OssClient;
import com.ej.oss.spring.boot.starter.factory.OssClientFactory;
import com.ej.oss.spring.boot.starter.properties.OssConfig;
import com.ej.oss.spring.boot.starter.properties.OssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
@ConditionalOnProperty(prefix = "ej.oss")
public class OssAutoConfiguration {

    @Resource
    private OssProperties ossProperties;

    @Bean
    public OssClientFactory ossClientFactory() {
        List<OssConfig> multi = ossProperties.getMulti();
        if (multi != null && !multi.isEmpty()) {
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
        if (config != null && !config.isBlank()) {
            OssClient client = new OssClient(config, Boolean.FALSE);
            return client;
        }
        return null;
    }
}
