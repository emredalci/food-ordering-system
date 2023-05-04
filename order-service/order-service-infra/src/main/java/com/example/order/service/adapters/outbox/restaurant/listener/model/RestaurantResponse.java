package com.example.order.service.adapters.outbox.restaurant.listener.model;

import com.example.order.service.order.model.RestaurantApprovalStatus;
import com.example.order.service.outbox.restaurant.usecase.OutboxRestaurantListenerUseCase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RestaurantResponse(String id,
                                 String sagaId,
                                 String orderId,
                                 String restaurantId,
                                 LocalDateTime createdAt,
                                 RestaurantApprovalStatus restaurantApprovalStatus,
                                 List<String> failureMessages) {

    public OutboxRestaurantListenerUseCase toUseCase() {
        return new OutboxRestaurantListenerUseCase(
                UUID.fromString(id),
                UUID.fromString(sagaId),
                UUID.fromString(orderId),
                UUID.fromString(restaurantId),
                createdAt,
                restaurantApprovalStatus,
                failureMessages);
    }
}
