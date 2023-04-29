package com.example.order.service.outbox.restaurant.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RestaurantProductPayload(@JsonProperty String id,
                                       @JsonProperty int quantity) {
}
