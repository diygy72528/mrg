package com.guyao.mrg.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author guyao
 * @date 2019/10/18 4:32 下午
 */
public class ValidKaptchaException extends AuthenticationException {
    public ValidKaptchaException(String msg) {
        super(msg);
    }
}
