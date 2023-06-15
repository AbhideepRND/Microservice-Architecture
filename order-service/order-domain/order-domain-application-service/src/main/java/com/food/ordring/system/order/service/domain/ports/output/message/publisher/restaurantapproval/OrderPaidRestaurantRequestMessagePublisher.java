package com.food.ordring.system.order.service.domain.ports.output.message.publisher.restaurantapproval;

import com.food.ordring.system.domain.events.publisher.DomainEventPublisher;
import com.food.ordring.system.order.service.domain.events.PaidOrderEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<PaidOrderEvent> {
}
