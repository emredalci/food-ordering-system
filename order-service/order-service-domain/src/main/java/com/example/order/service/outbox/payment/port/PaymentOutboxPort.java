package com.example.order.service.outbox.payment.port;

import com.example.order.service.order.event.OrderCreatedEvent;

public interface PaymentOutboxPort {

    void save(OrderCreatedEvent orderCreatedEvent);
}
