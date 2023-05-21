package com.example.restaurant.service.restaurant.event;

import com.example.restaurant.service.common.event.DomainEvent;
import com.example.restaurant.service.restaurant.model.Restaurant;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class RestaurantEvent extends DomainEvent {

    private Restaurant restaurant;

}
