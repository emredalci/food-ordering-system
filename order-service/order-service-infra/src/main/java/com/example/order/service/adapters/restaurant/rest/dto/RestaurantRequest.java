package com.example.order.service.adapters.restaurant.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RestaurantRequest(@NotNull UUID restaurantId,
                                @NotNull List<UUID> productIdList) {
}
