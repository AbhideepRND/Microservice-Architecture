package com.food.ordring.system.order.service.domain;

import com.food.ordring.system.order.service.domain.entity.Order;
import com.food.ordring.system.order.service.domain.entity.Restaurant;
import com.food.ordring.system.order.service.domain.events.CancelledOrderEvent;
import com.food.ordring.system.order.service.domain.events.CreateOrderEvent;
import com.food.ordring.system.order.service.domain.events.PaidOrderEvent;

import java.util.List;


/**
 * This method will call the required business method define in entities to validate the business process.
 * This service will return the events. The caller need to handle i.e. application service.
 * The application service will persist the underlying information before initiate events.
 */
public interface OrderServiceDomain {

    /**
     * To validate create order business and generate order. The validate os done through business entity.
     * @param order
     * @param resturent
     * @return
     */
    CreateOrderEvent validateAndInitiateOrder(Order order, Restaurant resturent);

    PaidOrderEvent payOrder(Order order);

    void approveOrder(Order order);

    CancelledOrderEvent cancelledOrderPayment(Order order, List<String> failureMessage);

    void cancelledOrder(Order order, List<String> failureMessage);

}
