package com.example.order.service.outbox.payment.port;

import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.saga.SagaStatus;

import java.util.List;
import java.util.UUID;

public interface OutboxPaymentPort {

    void save(OrderCreatedEvent orderCreatedEvent);

    List<OrderCreatedEvent> getPublishReadyEvents();

    List<UUID> getDeleteReadyEventIds();

    void deleteByIdList(List<UUID> deleteReadyEventIdList);

    OrderCreatedEvent getBySagaIdAndSagaStatus(UUID sagaId, SagaStatus ...sagaStatus);
}
