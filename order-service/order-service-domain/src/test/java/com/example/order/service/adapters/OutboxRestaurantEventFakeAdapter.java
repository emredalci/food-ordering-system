package com.example.order.service.adapters;

import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.restaurant.port.OutboxRestaurantEventPort;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;

@Slf4j
public class OutboxRestaurantEventFakeAdapter implements OutboxRestaurantEventPort {
    @Override
    public void publish(OrderPaidEvent event, BiConsumer<OrderPaidEvent, OutboxStatus> callback) {
      log.info("{} event is processed", event.getId().toString());
    }
}
