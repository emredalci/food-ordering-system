package com.example.payment.service.adapters.outbox.payment.jpa.repository;

import com.example.payment.service.adapters.outbox.payment.jpa.entity.OutboxOrderEntity;
import com.example.payment.service.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxOrderJpaRepository extends JpaRepository<OutboxOrderEntity, UUID> {

    Optional<List<OutboxOrderEntity>> findByOutboxStatus(OutboxStatus status);

    @Query("select e.id from OutboxOrderEntity e where e.outboxStatus = :status")
    Optional<List<UUID>> findDeleteReadyMessageIds(@Param("status") OutboxStatus status);

}
