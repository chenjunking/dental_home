package com.dental.home.app.exception;

/**
 * h5没有登录异常
 */
public class NotLoginException extends RuntimeException{
    private static final long serialVersionUID = -4803388936130320999L;
    public NotLoginException(String message) {
        super(message);
    }
}
