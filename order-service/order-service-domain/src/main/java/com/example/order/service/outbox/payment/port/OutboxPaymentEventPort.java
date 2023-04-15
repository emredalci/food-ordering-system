package com.example.order.service.outbox.payment.port;

import com.example.order.service.order.event.OrderCreatedEvent;
import com.example.order.service.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface OutboxPaymentEventPort {

    void publish(OrderCreatedEvent event, BiConsumer<OrderCreatedEvent, OutboxStatus> callback);
}
