package com.example.restaurant.service.product;

import com.example.restaurant.service.common.model.Money;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class Product {

    private UUID id;
    private String name;
    private Money price;
    private final int quantity;
    private boolean available;

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
