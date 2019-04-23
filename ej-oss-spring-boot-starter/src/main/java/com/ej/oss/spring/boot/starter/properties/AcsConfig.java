package com.ej.oss.spring.boot.starter.properties;

import com.ej.oss.spring.boot.starter.exception.OssConfigException;

public class AcsConfig extends OssConfig {

    private String arn;

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    @Override
    public void singleConfigCheck() {
        super.singleConfigCheck();
        if (arn == null || arn.trim().isEmpty()) {
            throw new OssConfigException(
                    this.getClass().getSimpleName() + "配置连接密码[arn]不能为空!");
        }
    }

}
