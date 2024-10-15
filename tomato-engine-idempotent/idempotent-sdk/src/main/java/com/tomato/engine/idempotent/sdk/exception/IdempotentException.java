package com.tomato.engine.idempotent.sdk.exception;

/**
 * 异常处理信息
 *
 * @author lizhifu
 * @since 2024/10/15
 */
public class IdempotentException extends RuntimeException {

    public IdempotentException(String message) {
        super(message);
    }
}
