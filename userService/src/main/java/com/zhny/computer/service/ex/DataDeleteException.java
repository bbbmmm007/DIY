package com.zhny.computer.service.ex;

public class DataDeleteException extends RuntimeException {
    public DataDeleteException() {
        super();
    }

    public DataDeleteException(String message) {
        super(message);
    }

    public DataDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataDeleteException(Throwable cause) {
        super(cause);
    }

    protected DataDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
