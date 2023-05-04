package com.example.order.service.outbox.restaurant.port;

import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.saga.SagaStatus;

import java.util.List;
import java.util.UUID;

public interface OutboxRestaurantPort {

    void save(OrderPaidEvent orderPaidEvent);

    List<OrderPaidEvent> getPublishReadyEvents();

    List<UUID> getDeleteReadyEventIds();

    void deleteByIdList(List<UUID> ids);

    OrderPaidEvent getBySagaIdAndSagaStatus(UUID sagaId, SagaStatus ...sagaStatuses);
}
