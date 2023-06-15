package com.food.ordring.system.order.service.domain;

import com.food.ordring.system.domain.events.publisher.DomainEventPublisher;
import com.food.ordring.system.order.service.domain.events.CreateOrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * This will publish event by using ApplicationEventPublisher of spring.
 * When ApplicationEventPublisher publish the event "TransactionalEventListener"
 * will listen the event i.e define in OrderCreatedEventApplicationListener over the method process.
 */
@Slf4j
@Component
public class ApplicationDomainEventPublisher implements ApplicationEventPublisherAware, DomainEventPublisher<CreateOrderEvent> {

    private ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(CreateOrderEvent domainEvent) {
        applicationEventPublisher.publishEvent(domainEvent);
        log.info("Order publisher event ");
    }
}
