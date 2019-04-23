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

    /**
     * 
     * @Description: 多AcsClient配置校验
     * @author Evan Jiang
     * @date 2019年4月23日 下午4:58:21
     */
    @Override
    public void multiCheck() {
        super.multiCheck();
    }

    /**
     * 
     * @Description: 单AcsClient配置校验
     * @author Evan Jiang
     * @date 2019年4月23日 下午5:00:17
     */
    @Override
    public void singleCheck() {
        super.singleCheck();
        if (arn == null || arn.trim().isEmpty()) {
            throw new OssConfigException(
                    this.getClass().getSimpleName() + "配置连接密码[arn]不能为空!");
        }
    }

    /**
     * 
     * @Description: 判断配置是否都为空,单个配置时使用,根据此方法确定是否要初始化AcsClient
     * @author Evan Jiang
     * @date 2019年4月23日 下午4:41:54 
     * @return
     */
    @Override
    public boolean isBlank() {
        return super.isBlank() && (arn != null && !arn.trim().isEmpty());
    }

}
