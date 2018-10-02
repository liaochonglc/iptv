package com.ido.iptv.exception;

/**
 * javaBean异常
 *
 * @author Jun
 * @date 2018-09-25 14:32
 */
public class BeanException extends GlobalException {

    public BeanException() {
        super();
    }

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }

    protected BeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
