package com.ido.iptv.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Jun
 * @date 2018-09-25 14:59
 */
public class InvalidJWTException extends AuthenticationException {

    public InvalidJWTException(String msg, Throwable t) {
        super(msg, t);
    }

    public InvalidJWTException(String msg) {
        super(msg);
    }
}

