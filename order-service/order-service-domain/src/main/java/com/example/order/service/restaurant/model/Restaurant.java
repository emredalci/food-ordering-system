package com.example.order.service.restaurant.model;

import com.example.order.service.product.model.Product;

import java.util.List;
import java.util.UUID;

public final class Restaurant {
    private final UUID id;
    private final List<Product> products;
    private boolean active;

    public Restaurant(UUID id, List<Product> products, boolean active) {
        this.id = id;
        this.products = products;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isActive() {
        return active;
    }
}
