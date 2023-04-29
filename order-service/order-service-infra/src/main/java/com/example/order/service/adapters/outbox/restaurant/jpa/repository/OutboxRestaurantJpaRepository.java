package com.example.order.service.adapters.outbox.restaurant.jpa.repository;

import com.example.order.service.adapters.outbox.restaurant.jpa.entity.OutboxRestaurantEntity;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.saga.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OutboxRestaurantJpaRepository extends JpaRepository<OutboxRestaurantEntity, UUID> {

    Optional<List<OutboxRestaurantEntity>> findByOutboxStatusAndSagaStatusIn(OutboxStatus outboxStatus, List<SagaStatus> sagaStatusList);

    @Query("select e.id from OutboxRestaurantEntity e where e.outboxStatus = :outboxStatus and e.sagaStatus in (:sagaStatusList)")
    Optional<List<UUID>> getDeleteReadyEventIds(@Param("outboxStatus") OutboxStatus outboxStatus, @Param("sagaStatusList") List<SagaStatus> sagaStatusList);

    void deleteByIdIn(List<UUID> ids);
}
