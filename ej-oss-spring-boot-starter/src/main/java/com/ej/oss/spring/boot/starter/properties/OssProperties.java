package com.ej.oss.spring.boot.starter.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ej.oss")
public class OssProperties {

    private List<OssConfig> multi = new ArrayList<>();
    private OssConfig single = new OssConfig();

    public List<OssConfig> getMulti() {
        return multi;
    }

    public void setMulti(List<OssConfig> multi) {
        this.multi = multi;
    }

    public OssConfig getSingle() {
        return single;
    }

    public void setSingle(OssConfig single) {
        this.single = single;
    }

    public void multiConfigCheck() {
        multi.forEach(OssConfig::multiConfigCheck);
    }

    public boolean singleIsBlank() {
        return (single.getKeyId() == null || single.getKeyId().trim().isEmpty())
                && (single.getKeySecret() == null
                        || single.getKeySecret().trim().isEmpty())
                && (single.getEndpoint() == null
                        || single.getEndpoint().trim().isEmpty())
                && (single.getBucketName() == null
                        || single.getBucketName().trim().isEmpty());
    }

    public void singleConfigCheck() {
        single.singleConfigCheck();
    }
}
