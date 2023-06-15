package com.food.ordring.system.order.service.domain.ports.output.message.publisher.payment;

import com.food.ordring.system.domain.events.publisher.DomainEventPublisher;
import com.food.ordring.system.order.service.domain.events.CancelledOrderEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<CancelledOrderEvent> {
}
