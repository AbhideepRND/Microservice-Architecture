package com.food.ordring.system.order.service.domain.events;

import com.food.ordring.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class PaidOrderEvent extends OrderEvent {

    public PaidOrderEvent(Order order, ZonedDateTime createAt) {
        super(order, createAt);
    }
}
