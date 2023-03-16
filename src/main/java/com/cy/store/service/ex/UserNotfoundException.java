package com.cy.store.service.ex;

/**
 * 无法通过uid找到user时的异常
 */
public class UserNotfoundException extends ServiceException{
    public UserNotfoundException() {
        super();
    }

    public UserNotfoundException(String message) {
        super(message);
    }

    public UserNotfoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotfoundException(Throwable cause) {
        super(cause);
    }

    protected UserNotfoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
