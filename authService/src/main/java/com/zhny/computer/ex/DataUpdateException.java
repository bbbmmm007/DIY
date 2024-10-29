package com.zhny.computer.ex;

public class DataUpdateException extends RuntimeException {
    public DataUpdateException() {
        super();
    }

    public DataUpdateException(String message) {
        super(message);
    }

    public DataUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataUpdateException(Throwable cause) {
        super(cause);
    }

    protected DataUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
