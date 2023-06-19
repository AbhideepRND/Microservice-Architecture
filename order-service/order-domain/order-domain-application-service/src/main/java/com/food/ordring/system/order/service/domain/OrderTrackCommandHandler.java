package com.food.ordring.system.order.service.domain;

import com.food.ordring.system.error.ErrorCode;
import com.food.ordring.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordring.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordring.system.order.service.domain.entity.Order;
import com.food.ordring.system.order.service.domain.exception.OrderNotFoundException;
import com.food.ordring.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordring.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordring.system.order.service.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;

    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> order = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getTrackingId()));
        if (order.isEmpty()) {
            log.error("Order not found by tracking ID {}", trackOrderQuery.getTrackingId());
            throw new OrderNotFoundException(ErrorCode.DOM_114, String.format("Order not found by tracking ID %s", trackOrderQuery.getTrackingId()));
        }
        return orderDataMapper.orderToTrackOrderResponse(order.get());
    }
}
