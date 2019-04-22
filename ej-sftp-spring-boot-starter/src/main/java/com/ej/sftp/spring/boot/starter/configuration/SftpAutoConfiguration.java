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
            sftpProperties.multiConfigCheck();
            multi.forEach(sftp -> {
                SftpClient sftpClient = new SftpClient(sftp);
                String alias = sftp.getAlias();
                sftpClientFactory.putSftpClient(alias, sftpClient, false);
            });
            return sftpClientFactory;
        }
        return null;
    }

    @Bean
    public SftpClient sftpClient() {
        if(!sftpProperties.singleIsBlank()) {
            sftpProperties.singleConfigCheck();
            SftpClient sftpClient = new SftpClient(sftpProperties.getSingle());
            return sftpClient;
        }
        return null;
    }
}
