package com.food.ordring.system.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {

    @NotNull
    private final UUID productId;

    @NotNull
    private final Integer quantity;

    @NotNull
    private String currency;

    @NotNull
    private final BigDecimal productPrice;

    @NotNull
    private final BigDecimal subTotal;
}
