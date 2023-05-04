package com.example.order.service.outbox.restaurant.usecase;

import com.example.order.service.common.usecase.UseCase;
import com.example.order.service.order.model.RestaurantApprovalStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OutboxRestaurantListenerUseCase (UUID id,
                                               UUID sagaId,
                                               UUID orderId,
                                               UUID restaurantId,
                                               LocalDateTime createdAt,
                                               RestaurantApprovalStatus restaurantApprovalStatus,
                                               List<String> failureMessages) implements UseCase {
}
