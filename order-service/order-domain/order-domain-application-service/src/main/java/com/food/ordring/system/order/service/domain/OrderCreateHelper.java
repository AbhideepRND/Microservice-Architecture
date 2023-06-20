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
public class OrderCreateHelper {

    private final OrderServiceDomain orderServiceDomain;

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderServiceDomain orderServiceDomain,
                                     CustomerRepository customerRepository,
                                     OrderRepository orderRepository,
                                     RestaurantRepository restaurantRepository,
                                     OrderDataMapper orderDataMapper) {

        this.orderServiceDomain = orderServiceDomain;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    public CreateOrderEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        CreateOrderEvent createOrderEvent = orderServiceDomain.validateAndInitiateOrder(order, restaurant);
        final Order orderCreated = createOrderEvent.getOrder();
        saveOrder(orderCreated);
        return createOrderEvent;
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
