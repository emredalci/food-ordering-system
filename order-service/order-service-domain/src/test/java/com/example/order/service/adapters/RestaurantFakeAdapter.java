package com.example.order.service.adapters;

import com.example.order.service.common.model.Money;
import com.example.order.service.product.model.Product;
import com.example.order.service.restaurant.model.Restaurant;
import com.example.order.service.restaurant.port.RestaurantPort;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class RestaurantFakeAdapter implements RestaurantPort {

    @Override
    public Restaurant retrieve(UUID restaurantId, List<UUID> productIdList) {
        Product product1 = new Product(productIdList.get(0), "product1", new Money(new BigDecimal("50.00")));
        Product product2 = new Product(productIdList.get(1), "product2", new Money(new BigDecimal("50.00")));
        return new Restaurant(restaurantId, List.of(product1, product2), true);
    }
}
