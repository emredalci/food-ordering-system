package com.example.restaurant.service.outbox.order.port;

import com.example.restaurant.service.outbox.order.model.OutboxOrderMessage;

public interface OutboxOrderPort {

    void save(OutboxOrderMessage message);

}
