package com.food.ordring.system.order.service.domain.events;

import com.food.ordring.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class CreateOrderEvent extends OrderEvent {

    public CreateOrderEvent(Order order, ZonedDateTime createAt) {
        super(order, createAt);
    }
}
