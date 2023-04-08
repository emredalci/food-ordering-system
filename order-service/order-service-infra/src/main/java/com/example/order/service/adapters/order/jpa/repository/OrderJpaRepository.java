package com.example.order.service.adapters.order.jpa.repository;

import com.example.order.service.adapters.order.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<OrderEntity> findByTrackingId(UUID trackingId);
}
