package com.food.ordring.system.domain.valueobject;

import com.food.ordring.system.domain.exception.CommonDomainException;
import com.food.ordring.system.domain.exception.DomainException;
import com.food.ordring.system.error.ErrorCode;

public enum  Currency {

    INR, USD, GBP, DFL;

    public static Currency getCurrency(String currency){
        Currency currencyEnum = Currency.valueOf(currency);
        if(currencyEnum == null){
            throw new CommonDomainException(ErrorCode.DOM_112, String.format("Currency %s is not available", currency));
        }
        return currencyEnum;
    }
}
