package com.zhny.computer.ex;

public class DataMatchException extends RuntimeException {
    public DataMatchException() {
        super();
    }

    public DataMatchException(String message) {
        super(message);
    }

    public DataMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataMatchException(Throwable cause) {
        super(cause);
    }

    protected DataMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
