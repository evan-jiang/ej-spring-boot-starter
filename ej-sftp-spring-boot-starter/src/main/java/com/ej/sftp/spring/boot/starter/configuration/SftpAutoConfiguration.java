package com.ej.sftp.spring.boot.starter.configuration;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ej.sftp.spring.boot.starter.client.SftpClient;
import com.ej.sftp.spring.boot.starter.factory.SftpClientFactory;
import com.ej.sftp.spring.boot.starter.properties.SftpConfig;
import com.ej.sftp.spring.boot.starter.properties.SftpProperties;

@Configuration
@EnableConfigurationProperties(SftpProperties.class)
public class SftpAutoConfiguration {

    @Resource
    private SftpProperties sftpProperties;

    @Bean
    public SftpClientFactory sftpClientFactory() {
        List<SftpConfig> multi = sftpProperties.getMulti();
        if (!multi.isEmpty()) {
            SftpClientFactory sftpClientFactory = new SftpClientFactory();
            multi.forEach(config -> {
                SftpClient sftpClient = new SftpClient(config, Boolean.TRUE);
                sftpClientFactory.putClient(sftpClient, false);
            });
            return sftpClientFactory;
        }
        return null;
    }

    @Bean
    public SftpClient sftpClient() {
        SftpConfig config = sftpProperties.getSingle();
        if (!config.isBlank()) {
            SftpClient sftpClient = new SftpClient(config, Boolean.FALSE);
            return sftpClient;
        }
        return null;
    }
}
