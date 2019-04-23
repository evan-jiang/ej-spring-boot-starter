package com.ej.sftp.spring.boot.starter.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "ej.sftp")
public class SftpProperties {

    @NestedConfigurationProperty
    private List<SftpConfig> multi = new ArrayList<SftpConfig>();
    private SftpConfig single = new SftpConfig();

    public List<SftpConfig> getMulti() {
        return multi;
    }

    public void setMulti(List<SftpConfig> multi) {
        this.multi = multi;
    }

    public SftpConfig getSingle() {
        return single;
    }

    public void setSingle(SftpConfig single) {
        this.single = single;
    }

}
