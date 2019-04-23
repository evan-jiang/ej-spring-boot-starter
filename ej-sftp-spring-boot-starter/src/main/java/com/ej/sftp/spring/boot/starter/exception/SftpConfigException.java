package com.ej.sftp.spring.boot.starter.exception;
/**
 * 
 * @Description: SFTP配置异常
 * @author Evan Jiang
 * @date 2019年4月20日 下午2:46:49 
 *
 */
public class SftpConfigException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SftpConfigException() {
        super();
    }

    public SftpConfigException(String message) {
        super(message);
    }

    public SftpConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public SftpConfigException(Throwable cause) {
        super(cause);
    }
}
