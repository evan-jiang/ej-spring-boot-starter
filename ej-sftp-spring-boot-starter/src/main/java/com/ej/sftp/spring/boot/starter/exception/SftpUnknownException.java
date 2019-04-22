package com.ej.sftp.spring.boot.starter.exception;
/**
 * 
 * @Description: SFTP找不到文件异常
 * @author Evan Jiang
 * @date 2019年4月20日 下午2:46:49 
 *
 */
public class SftpUnknownException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SftpUnknownException() {
        super();
    }

    public SftpUnknownException(String message) {
        super(message);
    }

    public SftpUnknownException(String message, Throwable cause) {
        super(message, cause);
    }

    public SftpUnknownException(Throwable cause) {
        super(cause);
    }
}
