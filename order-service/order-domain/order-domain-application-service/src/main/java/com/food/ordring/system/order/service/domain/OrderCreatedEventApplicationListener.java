package com.food.ordring.system.order.service.domain;

import com.food.ordring.system.order.service.domain.events.CreateOrderEvent;
import com.food.ordring.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * This will publish event when the transaction is completed.
 */
@Slf4j
@Component
public class OrderCreatedEventApplicationListener {

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;


    public OrderCreatedEventApplicationListener(OrderCreatedPaymentRequestMessagePublisher
                                                        orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    @TransactionalEventListener
    void process(CreateOrderEvent event){
        orderCreatedPaymentRequestMessagePublisher.publish(event);
    }

}
