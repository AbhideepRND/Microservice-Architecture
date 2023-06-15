package com.food.ordring.system.order.service.domain.exception;

import com.food.ordring.system.domain.exception.DomainException;
import com.food.ordring.system.error.ErrorCode;

public class OrderDomainException extends DomainException {
    private final ErrorCode errorCode;

    public OrderDomainException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public OrderDomainException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
