package com.food.ordring.system.order.service.domain;

import com.food.ordring.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordring.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordring.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordring.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordring.system.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * This class will handle the input request from client to create order.
 * We make the class as default so that client of the domain layer only have the access.
 * We are not exposing the implementation outside the package.
 */

@Slf4j
@Service
@Validated
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final TrackOrderCreateCommandHandler trackOrderCreateCommandHandler;

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler,
                                       TrackOrderCreateCommandHandler trackOrderCreateCommandHandler) {

        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.trackOrderCreateCommandHandler = trackOrderCreateCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {

        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {

        return trackOrderCreateCommandHandler.trackOrder(trackOrderQuery);
    }
}
