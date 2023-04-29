package com.example.order.service.adapters.outbox.restaurant.jpa;

import com.example.order.service.adapters.outbox.restaurant.jpa.entity.OutboxRestaurantEntity;
import com.example.order.service.adapters.outbox.restaurant.jpa.repository.OutboxRestaurantJpaRepository;
import com.example.order.service.adapters.outbox.restaurant.mapper.OutboxRestaurantMapper;
import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.restaurant.port.OutboxRestaurantPort;
import com.example.order.service.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxRestaurantDataAdapter implements OutboxRestaurantPort {

    private final OutboxRestaurantJpaRepository repository;

    @Override
    @Transactional
    public void save(OrderPaidEvent orderPaidEvent) {
        OutboxRestaurantEntity entity = OutboxRestaurantMapper.INSTANCE.map(orderPaidEvent);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderPaidEvent> getPublishReadyEvents() {
        return repository.findByOutboxStatusAndSagaStatusIn(OutboxStatus.STARTED, List.of(SagaStatus.PROCESSING))
                .orElseGet(List::of)
                .stream()
                .map(OutboxRestaurantMapper.INSTANCE::map)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getDeleteReadyEventIds() {
        return repository.getDeleteReadyEventIds(OutboxStatus.COMPLETED, List.of(SagaStatus.SUCCEEDED,
                        SagaStatus.FAILED,
                        SagaStatus.COMPENSATED))
                .orElseGet(List::of);

    }

    @Override
    @Transactional
    public void deleteByIdList(List<UUID> ids) {
        repository.deleteByIdIn(ids);
    }
}
