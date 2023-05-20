package com.example.payment.service.adapters.outbox.payment.jpa;

import com.example.payment.service.adapters.outbox.payment.jpa.entity.OutboxOrderEntity;
import com.example.payment.service.adapters.outbox.payment.jpa.repository.OutboxOrderJpaRepository;
import com.example.payment.service.adapters.outbox.payment.mapper.OutboxOrderMapper;
import com.example.payment.service.outbox.OutboxStatus;
import com.example.payment.service.outbox.order.model.OutboxOrderMessage;
import com.example.payment.service.outbox.order.port.OutboxOrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OutboxOrderDataAdapter implements OutboxOrderPort {

    private final OutboxOrderJpaRepository repository;

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
