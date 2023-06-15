package com.food.ordring.system.domain.exception;

import com.food.ordring.system.error.ErrorCode;

public abstract class DomainException extends RuntimeException{

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract ErrorCode getErrorCode();
}
