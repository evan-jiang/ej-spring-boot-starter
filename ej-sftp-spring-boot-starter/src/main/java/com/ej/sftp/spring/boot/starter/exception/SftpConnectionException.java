package com.ej.sftp.spring.boot.starter.exception;
/**
 * 
 * @Description: SFTP连接异常
 * @author Evan Jiang
 * @date 2019年4月20日 下午2:46:49 
 *
 */
public class SftpConnectionException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SftpConnectionException() {
        super();
    }

    public SftpConnectionException(String message) {
        super(message);
    }

    public SftpConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SftpConnectionException(Throwable cause) {
        super(cause);
    }
}
