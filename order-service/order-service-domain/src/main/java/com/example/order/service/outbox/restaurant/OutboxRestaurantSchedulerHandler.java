package com.example.order.service.outbox.restaurant;

import com.example.order.service.common.DomainComponent;
import com.example.order.service.common.usecase.NoUseCaseHandler;
import com.example.order.service.common.usecase.RegisterHelper;
import com.example.order.service.order.event.OrderPaidEvent;
import com.example.order.service.outbox.OutboxStatus;
import com.example.order.service.outbox.restaurant.model.OrderPaidEventProcessedSize;
import com.example.order.service.outbox.restaurant.port.OutboxRestaurantEventPort;
import com.example.order.service.outbox.restaurant.port.OutboxRestaurantPort;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@DomainComponent
@Slf4j
public class OutboxRestaurantSchedulerHandler extends RegisterHelper implements NoUseCaseHandler<OrderPaidEventProcessedSize> {

    private final OutboxRestaurantPort outboxRestaurantPort;
    private final OutboxRestaurantEventPort outboxRestaurantEventPort;

    public OutboxRestaurantSchedulerHandler(OutboxRestaurantPort outboxRestaurantPort, OutboxRestaurantEventPort outboxRestaurantEventPort) {
        this.outboxRestaurantPort = outboxRestaurantPort;
        this.outboxRestaurantEventPort = outboxRestaurantEventPort;
        register(OrderPaidEventProcessedSize.class, this);
    }

    @Override
    public OrderPaidEventProcessedSize handle() {
        List<OrderPaidEvent> events = outboxRestaurantPort.getPublishReadyEvents();
        if (Boolean.TRUE.equals(events.isEmpty())) {
            return new OrderPaidEventProcessedSize(0);
        }
        events.forEach(event -> outboxRestaurantEventPort.publish(event, this::updateOutboxStatus));
        return new OrderPaidEventProcessedSize(events.size());
    }

    private void updateOutboxStatus(OrderPaidEvent event, OutboxStatus status) {
        event.setOutboxStatus(status);
        outboxRestaurantPort.save(event);

    }
}
