package com.example.restaurant.service.outbox.order.model;

import com.example.restaurant.service.restaurant.event.RestaurantEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OutboxOrderEventPayload {
    @JsonProperty
    private String orderId;

    @JsonProperty
    private String restaurantId;

    @JsonProperty
    private String orderApprovalStatus;

    @JsonProperty
    private LocalDateTime createdAt;

    @JsonProperty
    private List<String> failureMessages;

    public static OutboxOrderEventPayload of(RestaurantEvent event) {
        return OutboxOrderEventPayload.builder()
                .orderId(event.getRestaurant().getOrderId().toString())
                .restaurantId(event.getRestaurant().getId().toString())
                .orderApprovalStatus(event.getRestaurant().getOrderApprovalStatus().toString())
                .createdAt(event.getCreatedAt())
                .failureMessages(event.getFailureMessages())
                .build();
    }
}
