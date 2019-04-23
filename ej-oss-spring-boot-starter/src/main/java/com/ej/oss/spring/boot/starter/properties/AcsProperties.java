package com.ej.oss.spring.boot.starter.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ej.acs")
public class AcsProperties {

    private List<AcsConfig> multi = new ArrayList<>();
    private AcsConfig single = new AcsConfig();

    public List<AcsConfig> getMulti() {
        return multi;
    }

    public void setMulti(List<AcsConfig> multi) {
        this.multi = multi;
    }

    public AcsConfig getSingle() {
        return single;
    }

    public void setSingle(AcsConfig single) {
        this.single = single;
    }

    public void multiConfigCheck() {
        multi.forEach(AcsConfig::multiConfigCheck);
    }

    public boolean singleIsBlank() {
        return (single.getKeyId() == null || single.getKeyId().trim().isEmpty())
                && (single.getKeySecret() == null
                        || single.getKeySecret().trim().isEmpty())
                && (single.getEndpoint() == null
                        || single.getEndpoint().trim().isEmpty())
                && (single.getBucketName() == null
                        || single.getBucketName().trim().isEmpty())
                && (single.getArn() == null
                        || single.getArn().trim().isEmpty());
    }

    public void singleConfigCheck() {
        single.singleConfigCheck();
    }
}
