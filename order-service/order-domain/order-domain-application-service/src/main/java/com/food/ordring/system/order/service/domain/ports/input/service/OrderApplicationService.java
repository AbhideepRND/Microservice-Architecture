package com.food.ordring.system.order.service.domain.ports.input.service;

import com.food.ordring.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordring.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordring.system.order.service.domain.dto.track.TrackOrderQuery;
import com.food.ordring.system.order.service.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

/**
 * Input ports are the interface implements in domain layer used by the client of domain layer .
 * This application is being used on client of domain layer to create order.
 */
public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
