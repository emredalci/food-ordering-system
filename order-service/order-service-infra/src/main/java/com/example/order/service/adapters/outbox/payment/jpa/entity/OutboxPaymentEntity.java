package com.example.order.service.adapters.outbox.payment.jpa.entity;

import com.example.order.service.order.model.OrderStatus;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.saga.SagaStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "outbox_payment")
@Getter
@Setter
public class OutboxPaymentEntity {

    @Id
    private UUID id;
    private UUID sagaId;
    private LocalDateTime createdAt;
    private String payload;
    private LocalDateTime processedAt;
    @Enumerated(EnumType.STRING)
    private SagaStatus sagaStatus;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;
    @Version
    private int version;


}
