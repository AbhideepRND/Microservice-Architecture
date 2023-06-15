package com.food.ordring.system.order.service.domain.events;

import com.food.ordring.system.domain.events.DomainEvent;
import com.food.ordring.system.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {

    private final Order order;
    private final ZonedDateTime createAt;

    public OrderEvent(Order order, ZonedDateTime createAt) {
        this.order = order;
        this.createAt = createAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreateAt() {
        return createAt;
    }
}
