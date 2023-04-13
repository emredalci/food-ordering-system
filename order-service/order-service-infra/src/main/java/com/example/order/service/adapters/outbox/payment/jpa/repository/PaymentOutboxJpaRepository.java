package com.example.order.service.adapters.outbox.payment.jpa.repository;

import com.example.order.service.adapters.outbox.payment.jpa.entity.PaymentOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutboxEntity, UUID> {
}
