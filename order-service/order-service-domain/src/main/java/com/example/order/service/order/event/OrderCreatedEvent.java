package com.example.order.service.order.event;

import com.example.order.service.common.event.DomainEvent;
import com.example.order.service.order.model.OrderStatus;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public final class OrderCreatedEvent implements DomainEvent {
    private final UUID id;
    private final UUID sagaId;
    private final LocalDateTime createdAt;
    private final String payload;
    private LocalDateTime processedAt;
    private SagaStatus sagaStatus;
    private OrderStatus orderStatus;
    private OutboxStatus outboxStatus;

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }
}
