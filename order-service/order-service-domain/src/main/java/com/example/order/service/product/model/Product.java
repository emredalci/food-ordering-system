package com.example.order.service.product.model;

import com.example.order.service.common.model.Money;

import java.util.UUID;

public final class Product {
    private final UUID id;
    private String name;
    private Money price;

    public Product(UUID id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price){
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }


}
