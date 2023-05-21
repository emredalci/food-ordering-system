package com.example.restaurant.service.outbox.order.model;


import com.example.restaurant.service.outbox.OutboxStatus;
import com.example.restaurant.service.restaurant.event.RestaurantEvent;
import com.example.restaurant.service.restaurant.model.OrderApprovalStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class OutboxOrderMessage {
    private UUID id;
    private UUID sagaId;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private String payload;
    private OrderApprovalStatus approvalStatus;
    private OutboxStatus outboxStatus;

    public static OutboxOrderMessage of(RestaurantEvent event, String payload, UUID sagaId) {
        return OutboxOrderMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(event.getCreatedAt())
                .processedAt(LocalDateTime.now())
                .payload(payload)
                .approvalStatus(event.getRestaurant().getOrderApprovalStatus())
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }
}
