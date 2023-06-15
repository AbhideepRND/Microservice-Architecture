package com.food.ordring.system.order.service.domain.events;

import com.food.ordring.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class CancelledOrderEvent extends OrderEvent {

    public CancelledOrderEvent(Order order, ZonedDateTime createAt) {
        super(order, createAt);
    }

}
