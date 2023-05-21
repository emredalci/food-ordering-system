package com.example.restaurant.service.restaurant.service;

import com.example.restaurant.service.common.DomainComponent;
import com.example.restaurant.service.restaurant.event.RestaurantEvent;
import com.example.restaurant.service.restaurant.event.RestaurantOrderApprovalEvent;
import com.example.restaurant.service.restaurant.event.RestaurantOrderRejectedEvent;
import com.example.restaurant.service.restaurant.model.OrderApprovalStatus;
import com.example.restaurant.service.restaurant.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

@DomainComponent
public class RestaurantService {

    public RestaurantEvent validateOrder(Restaurant restaurant) {
        List<String> failureMessages = new ArrayList<>();
        restaurant.validateOrder(failureMessages);
        if (failureMessages.isEmpty()) {
            restaurant.setOrderApprovalStatus(OrderApprovalStatus.APPROVED);
            return RestaurantOrderApprovalEvent.builder().restaurant(restaurant).build();
        }
        restaurant.setOrderApprovalStatus(OrderApprovalStatus.REJECTED);
        return RestaurantOrderRejectedEvent.builder()
                .restaurant(restaurant)
                .failureMessages(failureMessages)
                .build();
    }
}
