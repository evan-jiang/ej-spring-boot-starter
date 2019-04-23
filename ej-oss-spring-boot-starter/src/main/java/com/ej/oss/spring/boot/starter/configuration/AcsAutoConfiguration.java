package com.ej.oss.spring.boot.starter.configuration;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ej.oss.spring.boot.starter.client.AcsClient;
import com.ej.oss.spring.boot.starter.factory.AcsClientFactory;
import com.ej.oss.spring.boot.starter.properties.AcsConfig;
import com.ej.oss.spring.boot.starter.properties.AcsProperties;

@Configuration
@EnableConfigurationProperties(AcsProperties.class)
public class AcsAutoConfiguration {

    @Resource
    private AcsProperties acsProperties;

    @Bean
    public AcsClientFactory acsClientFactory() {
        List<AcsConfig> multi = acsProperties.getMulti();
        if (!multi.isEmpty()) {
            AcsClientFactory clientFactory = new AcsClientFactory();
            multi.forEach(config -> {
                AcsClient client = new AcsClient(config, Boolean.TRUE);
                clientFactory.putClient(client, Boolean.FALSE);
            });
            return clientFactory;
        }
        return null;
    }

    @Bean
    public AcsClient acsClient() {
        AcsConfig config = acsProperties.getSingle();
        if (!config.isBlank()) {
            AcsClient client = new AcsClient(config, Boolean.FALSE);
            return client;
        }
        return null;
    }
}
