package com.example.resturant.service.adapter.outbox.order.jpa.entity;


import com.example.restaurant.service.outbox.OutboxStatus;
import com.example.restaurant.service.restaurant.model.OrderApprovalStatus;
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
@Table(name = "outbox_order")
@Getter
@Setter
public class OutboxOrderEntity {

    @Id
    private UUID id;
    private UUID sagaId;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private String payload;
    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;
    @Enumerated(EnumType.STRING)
    private OrderApprovalStatus approvalStatus;
    @Version
    private int version;
}
