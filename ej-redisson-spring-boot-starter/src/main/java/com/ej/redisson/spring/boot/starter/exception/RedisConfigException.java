package com.ej.redisson.spring.boot.starter.exception;

public class RedisConfigException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RedisConfigException() {
        super();
    }

    public RedisConfigException(String message) {
        super(message);
    }

    public RedisConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisConfigException(Throwable cause) {
        super(cause);
    }
}
