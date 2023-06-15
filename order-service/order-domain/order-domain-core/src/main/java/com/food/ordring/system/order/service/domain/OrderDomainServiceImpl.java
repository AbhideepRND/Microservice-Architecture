package com.food.ordring.system.order.service.domain;

import com.food.ordring.system.domain.valueobject.ProductId;
import com.food.ordring.system.error.ErrorCode;
import com.food.ordring.system.order.service.domain.entity.Order;
import com.food.ordring.system.order.service.domain.entity.Product;
import com.food.ordring.system.order.service.domain.entity.Restaurant;
import com.food.ordring.system.order.service.domain.events.CancelledOrderEvent;
import com.food.ordring.system.order.service.domain.events.CreateOrderEvent;
import com.food.ordring.system.order.service.domain.events.PaidOrderEvent;
import com.food.ordring.system.order.service.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Order domian service implements the business logic other than business entity
 * which consume more than one AggreateRoot.
 */
@Slf4j
public class OrderDomainServiceImpl implements OrderServiceDomain {

    private final static String DEFAULT_UTC = "UTC";

    @Override
    public CreateOrderEvent validateAndInitiateOrder(Order order, Restaurant resturent) {
        validateResturent(resturent);
        setOrderProductInformation(order, resturent);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order id {} is initaited",order.getId().getValue());
        return new CreateOrderEvent(order, ZonedDateTime.now(ZoneId.of(DEFAULT_UTC)));
    }

    @Override
    public PaidOrderEvent payOrder(Order order) {
        order.pay();
        log.info("Order {} is paid",order.getId().getValue());
        return new PaidOrderEvent(order, ZonedDateTime.now(ZoneId.of(DEFAULT_UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approval();
        log.info("Order {} is approved",order.getId().getValue());
    }

    @Override
    public CancelledOrderEvent cancelledOrderPayment(Order order, List<String> failureMessage) {
        order.initCancelled(failureMessage);
        log.info("Order with id {} is initialize cancelled request",order.getId().getValue());
        return new CancelledOrderEvent(order, ZonedDateTime.now(ZoneId.of(DEFAULT_UTC)));
    }

    @Override
    public void cancelledOrder(Order order, List<String> failureMessage) {
        order.initCancelled(failureMessage);
        log.info("Order with id {} is cancelled ",order.getId().getValue());
    }

    private void validateResturent(Restaurant resturent) {
        if(!resturent.getActive()){
            throw new OrderDomainException(ErrorCode.DOM_109, "Restaurant is not active");
        }
    }

    private void setOrderProductInformation(Order order, Restaurant resturent) {

        final Map<ProductId, Product> collect = resturent.getProductList().stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        order.getOrderItemList().stream().forEach(orderItem -> {
            Product product = collect.get(orderItem.getProduct().getId());
            orderItem.getProduct().updateWithConfirmNameAndPrice(product.getName(), product.getPrice());
        });
    }

}
