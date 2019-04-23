package com.ej.oss.spring.boot.starter.exception;
/**
 * 
 * @Description: OSS配置异常
 * @author Evan Jiang
 * @date 2019年4月20日 下午2:46:49 
 *
 */
public class OssConfigException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public OssConfigException() {
        super();
    }

    public OssConfigException(String message) {
        super(message);
    }

    public OssConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssConfigException(Throwable cause) {
        super(cause);
    }
}
