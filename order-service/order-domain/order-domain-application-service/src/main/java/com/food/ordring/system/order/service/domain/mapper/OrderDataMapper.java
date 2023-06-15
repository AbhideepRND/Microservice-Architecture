package com.food.ordring.system.order.service.domain.mapper;

import com.food.ordring.system.domain.valueobject.*;
import com.food.ordring.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordring.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordring.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordring.system.order.service.domain.entity.Order;
import com.food.ordring.system.order.service.domain.entity.OrderItem;
import com.food.ordring.system.order.service.domain.entity.Product;
import com.food.ordring.system.order.service.domain.entity.Restaurant;
import com.food.ordring.system.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .resturentId(new ResturentId(createOrderCommand.getResturentId()))
                .productList(createOrderCommand.getItems().stream().map(item -> new Product(new ProductId(item.getProductId()))).toList())
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .resturentId(new ResturentId(createOrderCommand.getResturentId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice(), Currency.getCurrency(createOrderCommand.getCurrency())))
                .orderItemList(createOrderItemCommandToOrderItem(createOrderCommand.getItems()))
                .build();
    }

    private List<OrderItem> createOrderItemCommandToOrderItem(List<com.food.ordring.system.order.service.domain.dto.create.OrderItem> items) {

        items.stream().map(item -> OrderItem.builder()
                        .product(new Product(new ProductId(item.getProductId())))
                        .price(new Money(item.getProductPrice(), Currency.getCurrency(item.getCurrency())))
                        .quantity(item.getQuantity())
                        .subtotal(new Money(item.getSubTotal(), Currency.getCurrency(item.getCurrency()))).build())
                .toList();

        return null;
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(UUID.randomUUID(),
                address.getStreet(),
                address.getPostal(),
                address.getCity());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {

        return CreateOrderResponse.builder().orderStatus(order.getOrderStatus()).orderTrackingId(order.getTrackingId().getValue()).build();
    }
}
