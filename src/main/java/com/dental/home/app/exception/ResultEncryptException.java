package com.dental.home.app.exception;

/**
 * 返回参数加密异常
 */
public class ResultEncryptException extends RuntimeException{
    private static final long serialVersionUID = -4803388936130320999L;
    public ResultEncryptException(String message) {
        super(message);
    }
}
