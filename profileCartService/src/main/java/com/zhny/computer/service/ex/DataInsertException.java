package com.zhny.computer.service.ex;

public class DataInsertException extends RuntimeException {

    // 默认消息
    private static final String DEFAULT_MESSAGE = "数据插入异常";

    // 构造函数，接受自定义消息
    public DataInsertException(String customMessage) {
        super(DEFAULT_MESSAGE + ": " + customMessage); // 拼接默认消息和自定义消息
    }

    // 默认构造函数
    public DataInsertException() {
        super(DEFAULT_MESSAGE); // 使用默认消息
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
