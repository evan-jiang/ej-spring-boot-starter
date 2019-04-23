package com.ej.oss.spring.boot.starter.exception;
/**
 * 
 * @Description: OSS连接异常
 * @author Evan Jiang
 * @date 2019年4月20日 下午2:46:49 
 *
 */
public class OssConnectionException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public OssConnectionException() {
        super();
    }

    public OssConnectionException(String message) {
        super(message);
    }

    public OssConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssConnectionException(Throwable cause) {
        super(cause);
    }
}
