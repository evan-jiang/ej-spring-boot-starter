package com.ej.oss.spring.boot.starter.exception;
/**
 * 
 * @Description: OSS找不到文件异常
 * @author Evan Jiang
 * @date 2019年4月20日 下午2:46:49 
 *
 */
public class OssFileNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public OssFileNotFoundException() {
        super();
    }

    public OssFileNotFoundException(String message) {
        super(message);
    }

    public OssFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssFileNotFoundException(Throwable cause) {
        super(cause);
    }
}
