package com.food.ordring.system.order.service.domain.entity;

import com.food.ordring.system.domain.entity.BaseEntity;
import com.food.ordring.system.domain.valueobject.Money;
import com.food.ordring.system.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {

    private String name;
    private Money price;

    public Product(ProductId productId){
        super.setId(productId);
    }
    public Product(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public void updateWithConfirmNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public Money getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
