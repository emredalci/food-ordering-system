package com.example.order.service.adapters.restaurant.rest;


import com.example.order.service.adapters.restaurant.rest.dto.RestaurantRequest;
import com.example.order.service.restaurant.model.Restaurant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "restaurant", url = "${restaurant.address}")
public interface RestaurantClient {

    @PostMapping //TODO
    Restaurant getRestaurantInformation(RestaurantRequest request);



}
