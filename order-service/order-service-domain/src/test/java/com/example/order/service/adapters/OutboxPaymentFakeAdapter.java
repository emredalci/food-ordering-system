package com.example.order.service.adapters;

import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.order.model.OrderStatus;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;
import com.example.order.service.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
public class OutboxPaymentFakeAdapter implements OutboxPaymentPort {

    @Override
    public void save(OrderCreatedEvent orderCreatedEvent) {
        log.info("OrderCreatedEvent is saved");
    }

    @Override
    public List<OrderCreatedEvent> getPublishReadyEvents() {
        OrderCreatedEvent event1 = OrderCreatedEvent.builder().id(UUID.randomUUID()).build();
        OrderCreatedEvent event2 = OrderCreatedEvent.builder().id(UUID.randomUUID()).build();
        return List.of(event1,event2);
    }

    @Override
    public List<UUID> getDeleteReadyEventIds() {
        return List.of(UUID.randomUUID(), UUID.randomUUID());
    }

    @Override
    public void deleteByIdList(List<UUID> deleteReadyEventIdList) {
        log.info("{} events have just deleted", deleteReadyEventIdList.size());
    }

    @Override
    public OrderCreatedEvent getBySagaIdAndSagaStatus(UUID sagaId, SagaStatus... sagaStatus) {
        return new OrderCreatedEvent(UUID.randomUUID(),
                UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afa"),
                LocalDateTime.now(),
                "OrderPaid",
                LocalDateTime.now(),
                SagaStatus.STARTED,
                OrderStatus.PAID,
                OutboxStatus.STARTED);
    }
}
