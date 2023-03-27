package com.example.order.service.restaurant.model;

import com.example.order.service.product.model.Product;

import java.util.List;
import java.util.UUID;

public record Restaurant(UUID id, List<Product> products, boolean active) {
}
