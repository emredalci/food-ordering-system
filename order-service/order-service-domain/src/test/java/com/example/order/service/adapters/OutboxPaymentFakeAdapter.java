package com.example.order.service.adapters;

import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.outbox.payment.port.OutboxPaymentPort;

import java.util.List;
import java.util.UUID;

public class OutboxPaymentFakeAdapter implements OutboxPaymentPort {

    @Override
    public void save(OrderCreatedEvent orderCreatedEvent) {

    }

    @Override
    public List<OrderCreatedEvent> getPublishReadyEvents() {
        return null;
    }

    @Override
    public List<UUID> getDeleteReadyEventIds() {
        return null;
    }

    @Override
    public void deleteByIdList(List<UUID> deleteReadyEventIdList) {

    }
}
