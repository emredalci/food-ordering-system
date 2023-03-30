package com.example.order.service.customer.model;

import java.util.UUID;

public final class Customer {
    private final UUID id;

    public Customer(UUID id) {
        this.id = id;
    }

    public UUID id() {
        return id;
    }

}
