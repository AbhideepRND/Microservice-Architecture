package com.food.ordring.system.order.service.domain.entity;

import com.food.ordring.system.domain.entity.AggregateRoot;
import com.food.ordring.system.domain.valueobject.*;
import com.food.ordring.system.error.ErrorCode;
import com.food.ordring.system.order.service.domain.exception.OrderDomainException;
import com.food.ordring.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordring.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordring.system.order.service.domain.valueobject.TrackingId;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final ResturentId resturentId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> orderItemList;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessage;


    public void initializeOrder() {
        setId(new OrderId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    private void initializeOrderItems() {
        long orderItemId = 1L;
        for (OrderItem item : orderItemList) {
            item.initializeOrderItem(new OrderItemId(orderItemId), this.getId());
        }
    }

    public void validateOrder() {
        validateInitalOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateInitalOrder() {
        if (this.orderStatus != null || this.getId() != null) {
            throw new OrderDomainException(ErrorCode.DOM_101, "Order is not in correct state for initialization");
        }
    }

    private void validateTotalPrice() {
        if (this.getPrice() == null || !this.getPrice().isGreaterThanZero()) {
            throw new OrderDomainException(ErrorCode.DOM_102, "Order price must be greater than Zero");
        }
    }

    private void validateItemsPrice() {

        final Money price = this.getPrice();
        final Money reduce = this.getOrderItemList().stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubtotal();
        }).reduce(new Money(Money.ZERO.getMoney(), price.getCurrency()), Money::add);

        if (!price.equals(reduce)) {
            throw new OrderDomainException(ErrorCode.DOM_103, String.format("Total price: %s is not equal to order item total price %s", price.getMoney(), reduce.getMoney()));
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException(ErrorCode.DOM_104,
                    String.format("Item price: %s is not same as product price %s",
                            orderItem.getPrice().getMoney(),
                            orderItem.getProduct().getPrice().getMoney()));
        }
    }

    public void pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException(ErrorCode.DOM_105, String.format("Order is not in correct state for payment operation", orderStatus));
        }

        this.orderStatus = OrderStatus.PAID;
    }

    public void approval() {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException(ErrorCode.DOM_106, String.format("Order is not in correct state for approval operation", orderStatus));
        }
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void initCancelled(List<String> failureMessage) {
        if (orderStatus != OrderStatus.PAID) {
            throw new OrderDomainException(ErrorCode.DOM_107, String.format("Order is not in correct state for cancelling operation", orderStatus));
        }
        this.orderStatus = OrderStatus.CANCELLING;
        updateFailureMessage(failureMessage);
    }

    public void cancelled(List<String> failureMessage) {
        if (!(orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING)) {
            throw new OrderDomainException(ErrorCode.DOM_108, String.format("Order is not in correct state for cancelled operation", orderStatus));
        }
        this.orderStatus = OrderStatus.CANCELLED;
        updateFailureMessage(failureMessage);
    }

    private void updateFailureMessage(List<String> failureMessage) {
        if (this.failureMessage != null && failureMessage != null) {
            this.failureMessage.addAll(failureMessage.stream().filter(Objects::nonNull).toList());
        }
        if (this.failureMessage == null) {
            this.failureMessage = failureMessage;
        }
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public ResturentId getResturentId() {
        return resturentId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessage() {
        return failureMessage;
    }

    public static Builder builder() {
        return new Builder();
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        resturentId = builder.resturentId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        orderItemList = builder.orderItemList;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessage = builder.failureMessage;
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private ResturentId resturentId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> orderItemList;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessage;

        private Builder() {
        }

        public Builder id(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder resturentId(ResturentId val) {
            resturentId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder orderItemList(List<OrderItem> val) {
            orderItemList = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessage(List<String> val) {
            failureMessage = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
