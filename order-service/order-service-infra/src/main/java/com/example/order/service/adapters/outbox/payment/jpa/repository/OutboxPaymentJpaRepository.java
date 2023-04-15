package com.example.order.service.adapters.outbox.payment.jpa.repository;

import com.example.order.service.adapters.outbox.payment.jpa.entity.OutboxPaymentEntity;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.saga.SagaStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxPaymentJpaRepository extends JpaRepository<OutboxPaymentEntity, UUID> {

    Optional<List<OutboxPaymentEntity>> findByOutboxStatusAndSagaStatusIn(OutboxStatus outboxStatus, List<SagaStatus> sagaStatusList);

    @Query("select e.id from OutboxPaymentEntity e where e.orderStatus = :orderStatus and e.sagaStatus in (:sagaStatusList)")
    Optional<List<UUID>> getDeleteReadyEventIdList(@Param("orderStatus") OutboxStatus outboxStatus,
            @Param("sagaStatusList") List<SagaStatus> sagaStatusList);

    void deleteByIdIn(List<UUID> idList);

}
