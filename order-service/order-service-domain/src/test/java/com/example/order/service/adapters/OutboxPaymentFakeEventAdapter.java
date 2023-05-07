package com.example.order.service.adapters;

import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.payment.port.OutboxPaymentEventPort;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;

@Slf4j
public class OutboxPaymentFakeEventAdapter implements OutboxPaymentEventPort {
    @Override
    public void publish(OrderCreatedEvent event, BiConsumer<OrderCreatedEvent, OutboxStatus> callback) {
        log.info("{} event is published", event.getId().toString());
    }
}
