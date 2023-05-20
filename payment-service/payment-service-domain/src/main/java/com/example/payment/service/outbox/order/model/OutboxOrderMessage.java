package com.example.payment.service.outbox.order.model;

import com.example.payment.service.outbox.OutboxStatus;
import com.example.payment.service.payment.event.PaymentEvent;
import com.example.payment.service.payment.model.PaymentStatus;
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
    private PaymentStatus paymentStatus;
    private OutboxStatus outboxStatus;

    public static OutboxOrderMessage of(PaymentEvent event, String payload, UUID sagaId) {
        return OutboxOrderMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(event.getCreatedAt())
                .processedAt(LocalDateTime.now())
                .payload(payload)
                .paymentStatus(event.getPayment().getPaymentStatus())
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }
}
