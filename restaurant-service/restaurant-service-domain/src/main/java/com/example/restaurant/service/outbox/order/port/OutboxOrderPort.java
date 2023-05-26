package com.example.restaurant.service.outbox.order.port;

import com.example.restaurant.service.outbox.OutboxStatus;
import com.example.restaurant.service.outbox.order.model.OutboxOrderMessage;

import java.util.List;
import java.util.UUID;

public interface OutboxOrderPort {

    void save(OutboxOrderMessage message);

    List<OutboxOrderMessage> getByOutboxStatus(OutboxStatus outboxStatus);

    List<UUID> getIdsByOutboxStatus(OutboxStatus status);

    void deleteByIdList(List<UUID> messageIds);

}
