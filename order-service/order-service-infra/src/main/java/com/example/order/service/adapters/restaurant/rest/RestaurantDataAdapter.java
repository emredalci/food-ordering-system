package com.example.order.service.adapters.restaurant.rest;

import com.example.order.service.adapters.restaurant.rest.dto.RestaurantRequest;
import com.example.order.service.restaurant.model.Restaurant;
import com.example.order.service.restaurant.port.RestaurantPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.restaurant.noob-data-adapter", havingValue = "false", matchIfMissing = true)
public class RestaurantDataAdapter implements RestaurantPort {

    private final RestaurantClient restaurantClient;

    @Override
    public Restaurant retrieve(UUID restaurantId, List<UUID> productIdList) {
        //TODO
        return restaurantClient.getRestaurantInformation(new RestaurantRequest(restaurantId, productIdList));
    }
}
