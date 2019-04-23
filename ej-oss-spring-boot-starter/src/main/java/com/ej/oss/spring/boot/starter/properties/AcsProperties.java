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

}
