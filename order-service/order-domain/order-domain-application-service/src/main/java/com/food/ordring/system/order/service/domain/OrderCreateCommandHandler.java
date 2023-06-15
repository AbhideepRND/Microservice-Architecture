package com.food.ordring.system.order.service.domain;

import com.food.ordring.system.error.ErrorCode;
import com.food.ordring.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordring.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordring.system.order.service.domain.entity.Customer;
import com.food.ordring.system.order.service.domain.entity.Order;
import com.food.ordring.system.order.service.domain.entity.Restaurant;
import com.food.ordring.system.order.service.domain.events.CreateOrderEvent;
import com.food.ordring.system.order.service.domain.exception.OrderDomainException;
import com.food.ordring.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordring.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordring.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordring.system.order.service.domain.ports.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private final OrderServiceDomain orderServiceDomain;

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

    public OrderCreateCommandHandler(OrderServiceDomain orderServiceDomain,
                                     CustomerRepository customerRepository,
                                     OrderRepository orderRepository,
                                     RestaurantRepository restaurantRepository,
                                     OrderDataMapper orderDataMapper,
                                     ApplicationDomainEventPublisher applicationDomainEventPublisher) {

        this.orderServiceDomain = orderServiceDomain;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
        this.applicationDomainEventPublisher = applicationDomainEventPublisher;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order orderCommandToOrder = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        CreateOrderEvent createOrderEvent = orderServiceDomain.validateAndInitiateOrder(orderCommandToOrder, restaurant);
        final Order order = createOrderEvent.getOrder();
        saveOrder(order);
        applicationDomainEventPublisher.publish(createOrderEvent);
        return orderDataMapper.orderToCreateOrderResponse(order);
    }

    
    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {

        Restaurant orderCommandToRestaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> restaurantInformation = restaurantRepository.findRestaurantInformation(orderCommandToRestaurant);
        if(restaurantInformation.isEmpty()){
            log.warn("Could not find restaurant with restaurant id :- "+createOrderCommand.getResturentId());
            throw new OrderDomainException(ErrorCode.DOM_111, "Could not find restaurant with restaurant id :- "+createOrderCommand.getResturentId());
        }
        return restaurantInformation.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = this.customerRepository.findCustomer(customerId);
        if( customer.isEmpty()){
             log.warn("Could not find customer with customer id :- "+customerId);
             throw new OrderDomainException(ErrorCode.DOM_110, "Could not find customer with customer id :- "+customerId);
        }

    }

    @Transactional
    public Order saveOrder(Order order){
        Order saveOrder = orderRepository.save(order);
        if(saveOrder == null){
            log.error("Unable to save order");
            throw new OrderDomainException(ErrorCode.DOM_113, "Unable to save order");
        }
        log.info("Order is saved with order id :- "+order.getId().getValue());
        return saveOrder;
    }
}
