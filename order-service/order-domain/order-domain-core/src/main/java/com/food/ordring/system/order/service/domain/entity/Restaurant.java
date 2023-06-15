package com.food.ordring.system.order.service.domain.entity;

import com.food.ordring.system.domain.entity.AggregateRoot;
import com.food.ordring.system.domain.valueobject.ResturentId;

import java.util.List;

public class Restaurant extends AggregateRoot<ResturentId> {

    private final List<Product> productList;
    private Boolean isActive;

    private Restaurant(Builder builder) {
        super.setId(builder.resturentId);
        productList = builder.productList;
        isActive = builder.isActive;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Boolean getActive() {
        return isActive;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ResturentId resturentId;
        private List<Product> productList;
        private Boolean isActive;

        private Builder() {
        }

        public Builder resturentId(ResturentId val) {
            resturentId = val;
            return this;
        }

        public Builder productList(List<Product> val) {
            productList = val;
            return this;
        }

        public Builder isActive(Boolean val) {
            isActive = val;
            return this;
        }

        public Restaurant build() {
            return new Restaurant(this);
        }
    }
}
