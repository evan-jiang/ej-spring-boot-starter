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

}
