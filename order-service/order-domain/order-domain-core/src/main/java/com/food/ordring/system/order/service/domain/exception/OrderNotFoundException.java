package com.food.ordring.system.order.service.domain.exception;

import com.food.ordring.system.domain.exception.DomainException;
import com.food.ordring.system.error.ErrorCode;

public class OrderNotFoundException extends DomainException {

    private final ErrorCode errorCode;

    public OrderNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public OrderNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
