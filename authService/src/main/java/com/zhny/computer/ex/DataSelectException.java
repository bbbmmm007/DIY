package com.zhny.computer.ex;

public class DataSelectException extends RuntimeException {
    public DataSelectException() {
        super();
    }

    public DataSelectException(String message) {
        super(message);
    }

    public DataSelectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSelectException(Throwable cause) {
        super(cause);
    }

    protected DataSelectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
