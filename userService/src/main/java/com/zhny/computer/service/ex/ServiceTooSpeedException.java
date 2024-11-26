package com.zhny.computer.service.ex;

public class ServiceTooSpeedException extends RuntimeException {

    public ServiceTooSpeedException() {
        super();
    }

    public ServiceTooSpeedException(String message) {
        super(message);
    }

    public ServiceTooSpeedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceTooSpeedException(Throwable cause) {
        super(cause);
    }

    protected ServiceTooSpeedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
