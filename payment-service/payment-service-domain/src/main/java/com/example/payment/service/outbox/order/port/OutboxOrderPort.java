package com.example.payment.service.outbox.order.port;

import com.example.payment.service.outbox.order.model.OutboxOrderMessage;

public interface OutboxOrderPort {

    void save(OutboxOrderMessage message);
}
