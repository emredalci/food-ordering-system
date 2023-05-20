package com.example.payment.service.outbox.order.port;

import com.example.payment.service.outbox.OutboxStatus;
import com.example.payment.service.outbox.order.model.OutboxOrderMessage;

import java.util.function.BiConsumer;

public interface OutboxOrderEventPort {

    void publish(OutboxOrderMessage outboxOrderMessage, BiConsumer<OutboxOrderMessage, OutboxStatus> callback);
}
