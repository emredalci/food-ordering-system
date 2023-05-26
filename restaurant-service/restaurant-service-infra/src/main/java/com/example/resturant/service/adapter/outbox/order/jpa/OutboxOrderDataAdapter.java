package com.example.resturant.service.adapter.outbox.order.jpa;

import com.example.restaurant.service.outbox.OutboxStatus;
import com.example.restaurant.service.outbox.order.model.OutboxOrderMessage;
import com.example.restaurant.service.outbox.order.port.OutboxOrderPort;
import com.example.resturant.service.adapter.outbox.order.jpa.entity.OutboxOrderEntity;
import com.example.resturant.service.adapter.outbox.order.jpa.repository.OrderOutboxJpaRepository;
import com.example.resturant.service.adapter.outbox.order.mapper.OutboxOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxOrderDataAdapter implements OutboxOrderPort {

    private final OrderOutboxJpaRepository repository;

    @Override
    @Transactional
    public void save(OutboxOrderMessage message) {
        OutboxOrderEntity entity = OutboxOrderMapper.INSTANCE.map(message);
        repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OutboxOrderMessage> getByOutboxStatus(OutboxStatus outboxStatus) {
        return repository.findByOutboxStatus(outboxStatus)
                .orElseGet(List::of)
                .stream()
                .map(OutboxOrderMapper.INSTANCE::map)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getIdsByOutboxStatus(OutboxStatus status) {
        return repository.findDeleteReadyMessageIds(status).orElseGet(List::of);
    }

    @Override
    @Transactional
    public void deleteByIdList(List<UUID> messageIds) {
        repository.deleteAllById(messageIds);
    }
}
