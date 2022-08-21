package com.dental.home.app.exception;

/**
 * 参数解析异常
 */
public class ParamDecodeException extends RuntimeException{
    private static final long serialVersionUID = -4803388936130320999L;
    public ParamDecodeException(String message) {
        super(message);
    }
}
