package com.example.order.service.adapters.restaurant.rest;

import com.example.order.service.common.model.Money;
import com.example.order.service.product.model.Product;
import com.example.order.service.restaurant.model.Restaurant;
import com.example.order.service.restaurant.port.RestaurantPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "adapters.restaurant.noob-data-adapter", havingValue = "true", matchIfMissing = false)
public class RestaurantNoobAdapter implements RestaurantPort {
    @Override
    public Restaurant retrieve(UUID restaurantId, List<UUID> productIdList) {
        Product elma = new Product(productIdList.get(0), "elma", new Money(BigDecimal.valueOf(50.00)));
        Product armut = new Product(productIdList.get(1), "armut", new Money(BigDecimal.valueOf(50.00)));
        return new Restaurant(restaurantId, List.of(elma, armut), true);
    }
}
