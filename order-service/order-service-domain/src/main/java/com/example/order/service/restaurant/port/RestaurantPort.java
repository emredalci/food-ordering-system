package com.example.order.service.restaurant.port;

import com.example.order.service.restaurant.model.Restaurant;

import java.util.List;
import java.util.UUID;

public interface RestaurantPort {

    Restaurant retrieve(UUID restaurantId, List<UUID> productIdList);
}
