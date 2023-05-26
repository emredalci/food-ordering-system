package com.example.restaurant.service.outbox.order.port;

import com.example.restaurant.service.outbox.OutboxStatus;
import com.example.restaurant.service.outbox.order.model.OutboxOrderMessage;

import java.util.function.BiConsumer;

public interface OutboxOrderEventPort {

    void publish(OutboxOrderMessage outboxOrderMessage, BiConsumer<OutboxOrderMessage, OutboxStatus> callback);
}
