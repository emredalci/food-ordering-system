package com.example.order.service.adapters.outbox.payment.jpa;

import com.example.order.service.adapters.outbox.payment.jpa.entity.OutboxPaymentEntity;
import com.example.order.service.adapters.outbox.payment.jpa.repository.OutboxPaymentJpaRepository;
import com.example.order.service.adapters.outbox.payment.mapper.OutboxPaymentMapper;
import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;
import com.example.order.service.saga.SagaStatus;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxPaymentDataAdapter implements OutboxPaymentPort {

    private final OutboxPaymentJpaRepository repository;

    @Transactional
    @Override
    public void save(OrderCreatedEvent orderCreatedEvent) {
        OutboxPaymentEntity entity = OutboxPaymentMapper.INSTANCE.map(orderCreatedEvent);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderCreatedEvent> getPublishReadyEvents() {
        return repository.findByOutboxStatusAndSagaStatusIn(OutboxStatus.STARTED, List.of(SagaStatus.STARTED, SagaStatus.COMPENSATING))
                .orElseGet(List::of)
                .stream()
                .map(OutboxPaymentMapper.INSTANCE::map)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getDeleteReadyEventIds() {
        return repository.getDeleteReadyEventIdList(OutboxStatus.COMPLETED, List.of(SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,SagaStatus.COMPENSATED))
                .orElseGet(List::of);
    }

    @Override
    @Transactional
    public void deleteByIdList(List<UUID> deleteReadyEventIdList) {
        repository.deleteByIdIn(deleteReadyEventIdList);
    }

    @Transactional(readOnly = true)
    @Override
    public OrderCreatedEvent getBySagaIdAndSagaStatus(UUID sagaId, SagaStatus... sagaStatus) {
        return repository.findBySagaIdAndSagaStatusIn(sagaId, Arrays.asList(sagaStatus))
                .map(OutboxPaymentMapper.INSTANCE::map)
                .orElseThrow(() -> new OrderServiceBusinessException("not.found.outbox.payment.entity"));

    }

}
