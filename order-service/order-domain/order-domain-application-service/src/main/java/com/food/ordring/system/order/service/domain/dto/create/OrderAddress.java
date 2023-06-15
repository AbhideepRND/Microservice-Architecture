package com.food.ordring.system.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class OrderAddress {

    @NotNull
    @Max(value = 50)
    private final String street;

    @NotNull
    private final String postal;

    @NotNull
    @Max(value = 10)
    private final String city;
}
