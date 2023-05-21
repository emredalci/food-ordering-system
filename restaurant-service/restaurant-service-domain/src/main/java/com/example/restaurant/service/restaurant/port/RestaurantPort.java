package com.example.restaurant.service.restaurant.port;

import com.example.restaurant.service.restaurant.model.Restaurant;

import java.util.List;
import java.util.UUID;

public interface RestaurantPort {

    Restaurant getByIdAndProductIds(UUID id, List<UUID> productIds);

    void save(Restaurant restaurant);
}
