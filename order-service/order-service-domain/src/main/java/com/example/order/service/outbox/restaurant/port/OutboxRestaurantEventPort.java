package com.example.order.service.outbox.restaurant.port;

import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface OutboxRestaurantEventPort {

    void publish(OrderPaidEvent event, BiConsumer<OrderPaidEvent, OutboxStatus> callback);
}
