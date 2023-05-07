package com.example.order.service.adapters;

import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.outbox.restaurant.port.OutboxRestaurantPort;
import com.example.order.service.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
public class OutboxRestaurantFakeAdapter implements OutboxRestaurantPort {
    @Override
    public void save(OrderPaidEvent orderPaidEvent) {
        log.info("OrderPaidEvent is saved");
    }

    @Override
    public List<OrderPaidEvent> getPublishReadyEvents() {
        OrderPaidEvent event1 = OrderPaidEvent.builder().id(UUID.randomUUID()).build();
        OrderPaidEvent event2 = OrderPaidEvent.builder().id(UUID.randomUUID()).build();
        return List.of(event1, event2);
    }

    @Override
    public List<UUID> getDeleteReadyEventIds() {
        return List.of(UUID.randomUUID(), UUID.randomUUID());
    }

    @Override
    public void deleteByIdList(List<UUID> ids) {
        log.info("{} events have just deleted", ids.size());
    }

    @Override
    public OrderPaidEvent getBySagaIdAndSagaStatus(UUID sagaId, SagaStatus... sagaStatuses) {
        return null;
    }
}
