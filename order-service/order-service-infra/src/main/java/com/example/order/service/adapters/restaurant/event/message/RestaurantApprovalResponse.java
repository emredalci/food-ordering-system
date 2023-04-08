package com.example.order.service.adapters.restaurant.event.message;

import com.example.order.service.order.model.OrderApprovalStatus;

import java.time.Instant;
import java.util.List;

public record RestaurantApprovalResponse(String id,
                                         String sagaId,
                                         String orderId,
                                         String restaurantId,
                                         Instant createdAt,
                                         OrderApprovalStatus orderApprovalStatus,
                                         List<String> failureMessages) {
}
