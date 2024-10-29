package com.zhny.computer.ex;

public class DataInsertException extends RuntimeException {
    public DataInsertException() {
        super();
    }

    public DataInsertException(String message) {
        super(message);
    }

    public DataInsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataInsertException(Throwable cause) {
        super(cause);
    }

    protected DataInsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
