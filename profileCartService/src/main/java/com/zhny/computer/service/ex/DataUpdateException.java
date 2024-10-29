package com.zhny.computer.service.ex;

public class DataUpdateException extends RuntimeException {

    // 默认消息
    private static final String DEFAULT_MESSAGE = "数据更新异常";

    // 构造函数，接受自定义消息
    public DataUpdateException(String customMessage) {
        super(DEFAULT_MESSAGE + ": " + customMessage); // 拼接默认消息和自定义消息
    }

    // 默认构造函数
    public DataUpdateException() {
        super(DEFAULT_MESSAGE); // 使用默认消息
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
