package com.food.ordring.system.order.service.domain.entity;

import com.food.ordring.system.domain.entity.BaseEntity;
import com.food.ordring.system.domain.valueobject.Money;
import com.food.ordring.system.domain.valueobject.OrderId;
import com.food.ordring.system.order.service.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {

     private OrderId orderId;
     private final Product product;
     private final Integer quantity;
     private final Money price;
     private final Money subtotal;

     void initializeOrderItem(OrderItemId orderItemId, OrderId orderId) {
          super.setId(orderItemId);
          this.setOrderId(orderId);
     }

     public Boolean isPriceValid(){
          return this.price.isGreaterThanZero() &&
                  this.price.equals(product.getPrice()) &&
                  this.price.multiply(quantity).equals(subtotal);
     }

     private OrderItem(Builder builder) {
          super.setId(builder.orderItemId);
          setOrderId(builder.orderId);
          product = builder.product;
          quantity = builder.quantity;
          price = builder.price;
          subtotal = builder.subtotal;
     }

     public static Builder builder() {
          return new Builder();
     }

     public OrderId getOrderId() {
          return orderId;
     }

     public void setOrderId(OrderId orderId) {
          this.orderId = orderId;
     }

     public Product getProduct() {
          return product;
     }

     public Integer getQuantity() {
          return quantity;
     }

     public Money getPrice() {
          return price;
     }

     public Money getSubtotal() {
          return subtotal;
     }

     public static final class Builder {
          private OrderItemId orderItemId;
          private OrderId orderId;
          private Product product;
          private Integer quantity;
          private Money price;
          private Money subtotal;

          private Builder() {
          }

          public Builder orderItemId(OrderItemId val) {
               orderItemId = val;
               return this;
          }

          public Builder orderId(OrderId val) {
               orderId = val;
               return this;
          }

          public Builder product(Product val) {
               product = val;
               return this;
          }

          public Builder quantity(Integer val) {
               quantity = val;
               return this;
          }

          public Builder price(Money val) {
               price = val;
               return this;
          }

          public Builder subtotal(Money val) {
               subtotal = val;
               return this;
          }

          public OrderItem build() {
               return new OrderItem(this);
          }
     }
}
