package com.food.ordring.system.domain.exception;

import com.food.ordring.system.error.ErrorCode;

public class CommonDomainException extends DomainException{

    private final ErrorCode errorCode;

    public CommonDomainException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode =errorCode;
    }

    public CommonDomainException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode =errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
