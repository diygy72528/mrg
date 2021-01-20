package com.guyao.mrg.common.exception;

/**
 * @author guyao
 * @date 2020/4/24 1:42 下午
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String msg) {
        super(msg);
    }
}
