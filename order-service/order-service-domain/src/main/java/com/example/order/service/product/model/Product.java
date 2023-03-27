package com.example.order.service.product.model;

import com.example.order.service.common.model.Money;

import java.util.UUID;

public record Product(UUID id, String name, Money price) {
}
