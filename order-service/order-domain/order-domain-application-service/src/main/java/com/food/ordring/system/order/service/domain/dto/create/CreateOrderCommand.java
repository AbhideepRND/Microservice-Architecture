package com.food.ordring.system.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Contain the command information to create an order.
 * Application service ask below object from client of domain layer.
 */

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderCommand {

    @NotNull
    private final UUID customerId;

    @NotNull
    private UUID resturentId;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private String currency;

    @NotNull
    private final List<OrderItem> items;

    @NotNull
    private final OrderAddress address;

}
