package com.food.ordring.system.domain.events.publisher;

import com.food.ordring.system.domain.events.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent>{

    void publish(T domainEvent);
}
